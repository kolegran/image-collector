package com.github.kolegran.deviantart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kolegran.deviantart.configuration.DeviantArtConfiguration;
import com.github.kolegran.deviantart.image.Image;
import com.github.kolegran.deviantart.image.ImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.kolegran.deviantart.configuration.DeviantArtConfiguration.DEVIANT_ART_API_BROWSE_TAGS_URL;
import static java.util.Objects.isNull;

// TODO: split http client and service logic
@Singleton
class DeviantArtApiClient {

    private static final Logger logger = LoggerFactory.getLogger(DeviantArtApiClient.class);
    private static final String AUTH_RESULT_KEY = "authResultKey";

    private final Map<String, AuthorizationResult> authResultCache = new ConcurrentHashMap<>();
    private final DeviantArtConfiguration deviantArtConfiguration;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    DeviantArtApiClient(DeviantArtConfiguration deviantArtConfiguration) {
        this.deviantArtConfiguration = deviantArtConfiguration;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    private Map<String, List<ImageInfo>> browse(DARequestParameters parameters) {
        final Map<String, List<ImageInfo>> result = new HashMap<>();

        for (DATagInfo daTagInfo : parameters.getTags()) {

            // TODO: fix repeated token generation
            final AuthorizationResult authResult = authorizeWhenExpirationTimeIsUp();
            System.out.println(authResult.getAccessToken());

            final String browseTagUrl = getFormat(daTagInfo, authResult);
            final HttpResponse<String> response = sendHttpRequest(createHttpRequest(browseTagUrl));

            try {
                final ImageInfo imageInfo = objectMapper.readValue(response.body(), ImageInfo.class);
                result.computeIfAbsent(daTagInfo.getTag(), k -> new ArrayList<>()).add(imageInfo);
            } catch (JsonProcessingException e) {
                logger.error("Cannot parse response body to the ImageInfo object");
            }
        }
        return result;
    }

    private Map<String, List<ImageInfo>> isImageDownloadable(Map<String, List<ImageInfo>> imagesByTag) {
        final Map<String, List<ImageInfo>> copied = new HashMap<>(imagesByTag);
        for (Map.Entry<String, List<ImageInfo>> entry : copied.entrySet()) {
            for (ImageInfo imageInfo : entry.getValue()) {
                imageInfo.getImages().removeIf(Image::getDownloadable);
            }
        }
        return copied;
    }

    private Map<String, List<File>> createImageFiles(Map<String, List<ImageInfo>> downloadableImages) {
        final Map<String, List<File>> files = new HashMap<>();
        for (Map.Entry<String, List<ImageInfo>> entry : downloadableImages.entrySet()) {
            for (ImageInfo imageInfo : entry.getValue()) {
                for (Image image : imageInfo.getImages()) {
                    try {
                        final String accessToken = authorizeWhenExpirationTimeIsUp().getAccessToken();
                        final String url = String.format(DeviantArtConfiguration.DEVIANT_ART_API_DOWNLOAD_IMAGE_URL, image.getDeviationId(), accessToken);
                        files.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(new File(url));
                    } catch (Exception e) {
                        logger.error("Cannot create file for: {}, {}", image.getDeviationId(), image.getImageUrl());
                    }
                }
            }
        }
        return files;
    }

    private AuthorizationResult authorizeWhenExpirationTimeIsUp() {
        return authResultCache.compute(AUTH_RESULT_KEY, (k, existingAuthResult) -> {
            if (isNull(existingAuthResult)) {
                return authorize();
            }

            final LocalDateTime expirationTimeMinus5Minutes = existingAuthResult.getExpirationTime().minus(5, ChronoUnit.MINUTES);
            return LocalDateTime.now().isBefore(expirationTimeMinus5Minutes)
                ? authorize()
                : existingAuthResult;
        });
    }

    private AuthorizationResult authorize() {
        final HttpResponse<String> httpResponse = sendHttpRequest(createHttpRequest(deviantArtConfiguration.getAuthorizationUrl()));
        try {
            return new ObjectMapper().readValue(httpResponse.body(), AuthorizationResult.class);
        } catch (JsonProcessingException e) {
            logger.error("Cannot parse response body to the AuthorizationResult object");
            throw new DAAuthorizationException("Cannot authorize: " + httpResponse.statusCode());
        }
    }

    private String getFormat(DATagInfo daTagInfo, AuthorizationResult authResult) {
        return String.format(DEVIANT_ART_API_BROWSE_TAGS_URL, daTagInfo.getTag(), daTagInfo.getOffset(), daTagInfo.getLimit(), authResult.getAccessToken());
    }

    private HttpResponse<String> sendHttpRequest(HttpRequest httpRequest) {
        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new HttpCallException("Cannot make http request to Deviant Art");
        }
    }

    private HttpRequest createHttpRequest(String str) {
        return HttpRequest.newBuilder()
            .uri(URI.create(str))
            .timeout(Duration.ofMinutes(5))
            .header("Content-Type", "application/json")
            .GET()
            .build();
    }

    private static final class HttpCallException extends RuntimeException {

        private HttpCallException(String message) {
            super(message);
        }
    }

    private static final class DAAuthorizationException extends RuntimeException {

        public DAAuthorizationException(String message) {
            super(message);
        }
    }
}
