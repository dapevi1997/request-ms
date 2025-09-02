package co.com.crediya.api.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import co.com.crediya.api.security.util.JwtService;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationManagerTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private JwtAuthenticationManager jwtAuthenticationManager;

    private Authentication authentication;
    private Claims mockClaims;

    @BeforeEach
    void setUp() {
        // Arrange - Datos de prueba
        authentication = new UsernamePasswordAuthenticationToken("test-token", "test-token");
        mockClaims = mock(Claims.class);
    }

    @Test
    @DisplayName("Debería autenticar exitosamente con token válido")
    void deberiaAutenticarExitosamenteConTokenValido() {
        // Arrange
        when(mockClaims.getSubject()).thenReturn("usuario@example.com");
        when(mockClaims.get("roles", List.class)).thenReturn(List.of("CLIENT", "USER"));
        when(jwtService.extractAllClaims(anyString())).thenReturn(mockClaims);

        // Act
        Mono<Authentication> result = jwtAuthenticationManager.authenticate(authentication);

        // Assert
        StepVerifier.create(result).assertNext(auth -> {
            assertThat(auth).isInstanceOf(UsernamePasswordAuthenticationToken.class);
            assertThat(auth.getName()).isEqualTo("usuario@example.com");
            assertThat(auth.getAuthorities()).hasSize(2);
            assertThat(auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("CLIENT"))).isTrue();
            assertThat(auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("USER"))).isTrue();
        }).verifyComplete();
    }

    @Test
    @DisplayName("Debería fallar la autenticación con token inválido")
    void deberiaFallarLaAutenticacionConTokenInvalido() {
        // Arrange
        when(jwtService.extractAllClaims(anyString()))
                .thenThrow(new RuntimeException("Token inválido"));

        // Act
        Mono<Authentication> result = jwtAuthenticationManager.authenticate(authentication);

        // Assert
        StepVerifier.create(result).expectError(RuntimeException.class).verify();
    }
}
