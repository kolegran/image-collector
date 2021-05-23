package com.github.kolegran.deviantart;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Introspected
public class AuthorizationResult {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("status")
    private String status;

    private LocalDateTime expirationTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        this.expirationTime = LocalDateTime.now().plus(expiresIn, ChronoUnit.SECONDS); // created for easy check of expiring
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    @Override
    public String toString() {
        return "AuthorizationResult{" +
            "accessToken='" + accessToken + '\'' +
            ", tokenType='" + tokenType + '\'' +
            ", expiresIn=" + expiresIn +
            ", status='" + status + '\'' +
            ", expirationTime=" + expirationTime +
            '}';
    }
}
