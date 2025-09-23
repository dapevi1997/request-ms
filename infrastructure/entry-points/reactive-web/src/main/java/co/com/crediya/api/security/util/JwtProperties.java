package co.com.crediya.api.security.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties(prefix = "jwt")
@Profile("dev")
public record JwtProperties(
        String secret,
        Long expiration
) {
}
