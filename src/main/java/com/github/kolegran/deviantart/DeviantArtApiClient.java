package com.github.kolegran.deviantart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kolegran.deviantart.configuration.DeviantArtConfiguration;
import com.github.kolegran.deviantart.image.ImageInfo;

import javax.inject.Singleton;
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

@Singleton
class DeviantArtApiClient {

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

    Map<String, List<ImageInfo>> browseTopImagesByTag(DARequestParameters parameters) {
        final Map<String, List<ImageInfo>> result = new HashMap<>();

        for (DATag daTag : parameters.getTags()) {
            authorizeWhenExpirationTimeIsUp();
            final String accessToken = authResultCache.get(AUTH_RESULT_KEY).getAccessToken();

            final String browseTagUrl = String.format(DEVIANT_ART_API_BROWSE_TAGS_URL, daTag.getTag(), daTag.getOffset(), daTag.getLimit(), accessToken);
            final HttpResponse<String> response = sendHttpRequest(createHttpRequest(browseTagUrl));

            try {
                final ImageInfo imageInfo = objectMapper.readValue(response.body(), ImageInfo.class);
                result.computeIfAbsent(daTag.getTag(), k -> new ArrayList<>()).add(imageInfo);
            } catch (JsonProcessingException e) {
                // TODO: add slf4j logger
            }
        }
        return result;
    }

    private void authorizeWhenExpirationTimeIsUp() {
        final AuthorizationResult authResult = authResultCache.get(AUTH_RESULT_KEY);
        if (isNull(authResult)) {
            authorize();
            return;
        }

        final LocalDateTime expirationTimeMinus5Minutes = authResult.getExpirationTime().minus(5, ChronoUnit.MINUTES);
        if (LocalDateTime.now().isBefore(expirationTimeMinus5Minutes)) {
            authorize();
        }
    }

    private void authorize() {
        final HttpResponse<String> httpResponse = sendHttpRequest(createHttpRequest(deviantArtConfiguration.getAuthorizationUrl()));
        try {
            final AuthorizationResult authorizationResult = new ObjectMapper().readValue(httpResponse.body(), AuthorizationResult.class);
            authResultCache.put(AUTH_RESULT_KEY, authorizationResult);
        } catch (JsonProcessingException e) {
            // TODO: add slf4j logger
        }
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
}
