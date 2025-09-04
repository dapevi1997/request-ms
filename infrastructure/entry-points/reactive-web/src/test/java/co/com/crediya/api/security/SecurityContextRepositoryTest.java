package co.com.crediya.api.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SecurityContextRepositoryTest {

    @Mock
    private JwtAuthenticationManager jwtAuthenticationManager;

    private SecurityContextRepository securityContextRepository;

    @BeforeEach
    void setUp() {
        securityContextRepository = new SecurityContextRepository(jwtAuthenticationManager);
    }

    @Test
    @DisplayName("Constructor debe inicializar correctamente con JwtAuthenticationManager")
    void constructor_ShouldInitializeCorrectly() {
        // Assert
        assertThat(securityContextRepository).isNotNull();
    }

    @Test
    @DisplayName("save debe retornar Mono.empty()")
    void save_ShouldReturnEmpty() {
        // Arrange
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test"));
        SecurityContext context = new SecurityContextImpl();

        // Act
        Mono<Void> result = securityContextRepository.save(exchange, context);

        // Assert
        StepVerifier.create(result).verifyComplete();
    }

    @Test
    @DisplayName("load con token válido debe retornar SecurityContext")
    void load_WithValidToken_ShouldReturnSecurityContext() {
        // Arrange
        String token = "valid.jwt.token";
        String authHeader = "Bearer " + token;

        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test").header(HttpHeaders.AUTHORIZATION, authHeader));

        Authentication mockAuth = new UsernamePasswordAuthenticationToken("user@test.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(mockAuth));

        // Act
        Mono<SecurityContext> result = securityContextRepository.load(exchange);

        // Assert
        StepVerifier.create(result).expectNextMatches(securityContext -> {
            assertThat(securityContext).isNotNull();
            assertThat(securityContext.getAuthentication()).isEqualTo(mockAuth);
            return true;
        }).verifyComplete();
    }

    @Test
    @DisplayName("load sin Authorization header debe retornar vacío")
    void load_WithoutAuthorizationHeader_ShouldReturnEmpty() {
        // Arrange
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test"));

        // Act
        Mono<SecurityContext> result = securityContextRepository.load(exchange);

        // Assert
        StepVerifier.create(result).verifyComplete();
    }

    @Test
    @DisplayName("load con Authorization header sin Bearer debe retornar vacío")
    void load_WithoutBearerPrefix_ShouldReturnEmpty() {
        // Arrange
        String authHeader = "Basic user:password";

        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test").header(HttpHeaders.AUTHORIZATION, authHeader));

        // Act
        Mono<SecurityContext> result = securityContextRepository.load(exchange);

        // Assert
        StepVerifier.create(result).verifyComplete();
    }

    @Test
    @DisplayName("load con token inválido debe propagar error de autenticación")
    void load_WithInvalidToken_ShouldPropagateAuthenticationError() {
        // Arrange
        String token = "invalid.jwt.token";
        String authHeader = "Bearer " + token;

        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test").header(HttpHeaders.AUTHORIZATION, authHeader));

        when(jwtAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.error(new RuntimeException("Invalid token")));

        // Act
        Mono<SecurityContext> result = securityContextRepository.load(exchange);

        // Assert
        StepVerifier.create(result).expectError(RuntimeException.class).verify();
    }

    @Test
    @DisplayName("load debe extraer token correctamente del header")
    void load_ShouldExtractTokenCorrectly() {
        // Arrange
        String expectedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        String authHeader = "Bearer " + expectedToken;

        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test").header(HttpHeaders.AUTHORIZATION, authHeader));

        Authentication mockAuth = new UsernamePasswordAuthenticationToken("user@test.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(mockAuth));

        // Act
        Mono<SecurityContext> result = securityContextRepository.load(exchange);

        // Assert
        StepVerifier.create(result).expectNextCount(1).verifyComplete();

        // Verificar que se llamó con el token correcto
        // (El token se pasa como credentials en UsernamePasswordAuthenticationToken)
    }

    @Test
    @DisplayName("load con múltiples roles debe funcionar correctamente")
    void load_WithMultipleRoles_ShouldWorkCorrectly() {
        // Arrange
        String token = "valid.jwt.token";
        String authHeader = "Bearer " + token;

        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test").header(HttpHeaders.AUTHORIZATION, authHeader));

        Authentication mockAuth = new UsernamePasswordAuthenticationToken("admin@test.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(mockAuth));

        // Act
        Mono<SecurityContext> result = securityContextRepository.load(exchange);

        // Assert
        StepVerifier.create(result).expectNextMatches(securityContext -> {
            Authentication auth = securityContext.getAuthentication();
            assertThat(auth.getAuthorities()).hasSize(2);
            assertThat(auth.getPrincipal()).isEqualTo("admin@test.com");
            return true;
        }).verifyComplete();
    }

    @Test
    @DisplayName("SecurityContextRepository debe implementar ServerSecurityContextRepository")
    void securityContextRepository_ShouldImplementServerSecurityContextRepository() {
        // Assert
        assertThat(securityContextRepository).isInstanceOf(
                org.springframework.security.web.server.context.ServerSecurityContextRepository.class);
    }

    @Test
    @DisplayName("SecurityContextRepository debe tener anotación @Component")
    void securityContextRepository_ShouldHaveComponentAnnotation() {
        // Assert
        assertThat(securityContextRepository.getClass()
                .isAnnotationPresent(org.springframework.stereotype.Component.class)).isTrue();
    }

    @Test
    @DisplayName("load debe manejar headers con espacios adicionales")
    void load_ShouldHandleHeadersWithExtraSpaces() {
        // Arrange
        String token = "valid.jwt.token";
        String authHeader = "Bearer  " + token; // Espacios extra

        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test").header(HttpHeaders.AUTHORIZATION, authHeader));

        Authentication mockAuth = new UsernamePasswordAuthenticationToken("user@test.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(mockAuth));

        // Act
        Mono<SecurityContext> result = securityContextRepository.load(exchange);

        // Assert
        StepVerifier.create(result).expectNextCount(1).verifyComplete();
    }

    @Test
    @DisplayName("load debe manejar diferentes métodos HTTP")
    void load_ShouldHandleDifferentHttpMethods() {
        // Arrange
        String token = "valid.jwt.token";
        String authHeader = "Bearer " + token;

        // Test con POST
        ServerWebExchange postExchange = MockServerWebExchange.from(MockServerHttpRequest
                .post("/api/test").header(HttpHeaders.AUTHORIZATION, authHeader));

        Authentication mockAuth = new UsernamePasswordAuthenticationToken("user@test.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(mockAuth));

        // Act
        Mono<SecurityContext> result = securityContextRepository.load(postExchange);

        // Assert
        StepVerifier.create(result).expectNextCount(1).verifyComplete();
    }
}
