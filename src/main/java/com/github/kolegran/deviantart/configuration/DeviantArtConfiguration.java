package com.github.kolegran.deviantart.configuration;

import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Singleton
public class DeviantArtConfiguration {

    static final String DEVIANT_ART_API_URL = "https://www.deviantart.com";
    public static final String DEVIANT_ART_API_BROWSE_TAGS_URL = DEVIANT_ART_API_URL + "/api/v1/oauth2/browse/tags?tag=%s&offset=%d&limit=%d&access_token=%s";
    private static final String DEVIANT_ART_API_AUTH_URL = "/oauth2/token";

    private final String authorizationUrl;

    public DeviantArtConfiguration(
        @Value("${deviant.art.client.id}") String clientId,
        @Value("${deviant.art.client.secret}") String clientSecret
    ) {
        final String authorizationParameters = "?grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;
        this.authorizationUrl = DEVIANT_ART_API_URL + DEVIANT_ART_API_AUTH_URL + authorizationParameters;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }
}
