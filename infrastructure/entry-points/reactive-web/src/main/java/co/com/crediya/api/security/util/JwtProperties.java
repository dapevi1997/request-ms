package co.com.crediya.api.security.util;

public record JwtProperties(
        String secret,
        Long expiration
) {
}
