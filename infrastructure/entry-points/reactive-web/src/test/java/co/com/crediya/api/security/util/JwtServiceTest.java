package co.com.crediya.api.security.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private JwtService jwtService;

    private final String secret = "12345678901234567890123456789012"; // 32 chars para HMAC-SHA256
    private final Long expiration = 3600L; // 1 hora

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties(secret, expiration);
        jwtService = new JwtService(jwtProperties);

        userDetails = new User(
                "usuario@example.com",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    @DisplayName("Debería generar un token válido con el username y roles")
    void deberiaGenerarTokenValido() {
        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertThat(token).isNotNull();

        String usernameExtraido = jwtService.extractUsername(token);
        assertThat(usernameExtraido).isEqualTo(userDetails.getUsername());

        Claims claims = jwtService.extractAllClaims(token);
        assertThat(claims.get("roles")).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("Debería validar token correctamente")
    void deberiaValidarTokenCorrectamente() {
        // Arrange
        String token = jwtService.generateToken(userDetails);

        // Act
        Boolean valido = jwtService.validateToken(token, userDetails.getUsername());

        // Assert
        assertThat(valido).isTrue();
    }

    @Test
    @DisplayName("Debería extraer la fecha de expiración del token")
    void deberiaExtraerFechaDeExpiracion() {
        // Arrange
        String token = jwtService.generateToken(userDetails);

        // Act
        Date expirationDate = jwtService.extractExpiration(token);

        // Assert
        assertThat(expirationDate).isAfter(new Date());
    }

    @Test
    @DisplayName("Debería retornar false al validar token inválido")
    void deberiaRetornarFalseConTokenInvalido() {
        // Arrange
        String tokenInvalido = "invalid.token.value";

        // Act
        boolean valido = jwtService.validateToken(tokenInvalido, userDetails.getUsername());

        // Assert
        assertThat(valido).isFalse();
    }
}
