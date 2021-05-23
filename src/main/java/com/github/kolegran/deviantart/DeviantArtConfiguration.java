package com.github.kolegran.deviantart;

import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Singleton
class DeviantArtConfiguration {

    static final String DEVIANT_ART_API_URL = "https://www.deviantart.com/oauth2/token";

    private final String authorizationUrl;

    DeviantArtConfiguration(
        @Value("${deviant.art.client.id}") String clientId,
        @Value("${deviant.art.client.secret}") String clientSecret
    ) {
        final String authorizationParameters = "?grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;
        this.authorizationUrl = DEVIANT_ART_API_URL + authorizationParameters;
    }

    String getAuthorizationUrl() {
        return authorizationUrl;
    }
}
