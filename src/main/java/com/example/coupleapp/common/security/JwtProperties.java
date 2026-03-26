package com.example.coupleapp.common.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.jwt")
public record JwtProperties(
        String issuer,
        long accessTokenTtlMinutes,
        long refreshTokenTtlDays,
        String secret
) {
}
