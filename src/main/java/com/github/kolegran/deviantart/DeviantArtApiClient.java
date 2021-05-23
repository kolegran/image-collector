package com.github.kolegran.deviantart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

@Singleton
class DeviantArtApiClient {

    private static final String AUTH_RESULT_KEY = "authResultKey";

    private final Map<String, AuthorizationResult> authResultCache = new ConcurrentHashMap<>();
    private final DeviantArtConfiguration deviantArtConfiguration;
    private final HttpClient httpClient;

    DeviantArtApiClient(DeviantArtConfiguration deviantArtConfiguration) {
        this.deviantArtConfiguration = deviantArtConfiguration;
        this.httpClient = HttpClient.newHttpClient();
    }

    // TODO: add logic for browsing images by tag
    void browseTopImagesByTag() {
        authorizeWhenExpirationTimeIsUp();
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
        final HttpRequest httpRequest = createHttpRequest(deviantArtConfiguration.getAuthorizationUrl());
        final HttpResponse<String> httpResponse = sendHttpRequest(httpRequest);
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
            .timeout(Duration.ofMinutes(1))
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
