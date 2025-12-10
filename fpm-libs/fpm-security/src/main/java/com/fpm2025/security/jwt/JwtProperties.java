package com.fpm2025.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private Long expiration = 3600000L; // 1 hour
    private Long refreshExpiration = 86400000L; // 24 hours
    private String header = "Authorization";
    private String prefix = "Bearer ";
}
