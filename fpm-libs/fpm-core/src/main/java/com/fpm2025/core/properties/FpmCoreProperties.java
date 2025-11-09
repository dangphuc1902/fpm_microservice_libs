package com.fpm2025.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fpm.core")
@Data
public class FpmCoreProperties {

    private JwtConfig jwt = new JwtConfig();
    private RedisConfig redis = new RedisConfig();
    private PageConfig page = new PageConfig();

    @Data
    public static class JwtConfig {
        private String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        private Long expiration = 86400000L;
        private Long refreshExpiration = 604800000L;
    }

    @Data
    public static class RedisConfig {
        private String host = "localhost";
        private Integer port = 6379;
        private Long timeout = 60L;
        private String password;
    }

    @Data
    public static class PageConfig {
        private Integer defaultSize = 20;
        private Integer maxSize = 100;
    }
}
