package co.com.crediya.api.security.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtPropertiesTest {

    @Test
    void deberiaCrearJwtPropertiesConValores() {
        // Arrange
        String secret = "mi-clave-secreta";
        Long expiration = 3600L;

        // Act
        JwtProperties jwtProperties = new JwtProperties(secret, expiration);

        // Assert
        assertThat(jwtProperties).isNotNull();
        assertThat(jwtProperties.secret()).isEqualTo(secret);
        assertThat(jwtProperties.expiration()).isEqualTo(expiration);
    }

    @Test
    void deberiaSoportarEqualsYHashCode() {
        // Arrange
        JwtProperties jwt1 = new JwtProperties("clave", 1000L);
        JwtProperties jwt2 = new JwtProperties("clave", 1000L);
        JwtProperties jwt3 = new JwtProperties("otra-clave", 2000L);

        // Act & Assert
        assertThat(jwt1).isEqualTo(jwt2);
        assertThat(jwt1).hasSameHashCodeAs(jwt2);

        assertThat(jwt1).isNotEqualTo(jwt3);
        assertThat(jwt1.hashCode()).isNotEqualTo(jwt3.hashCode());
    }

    @Test
    void deberiaTenerToStringCorrecto() {
        // Arrange
        JwtProperties jwtProperties = new JwtProperties("clave-test", 5000L);

        // Act
        String toString = jwtProperties.toString();

        // Assert
        assertThat(toString).contains("clave-test");
        assertThat(toString).contains("5000");
    }
}
