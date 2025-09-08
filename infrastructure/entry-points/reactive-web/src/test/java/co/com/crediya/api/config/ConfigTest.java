package co.com.crediya.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigTest {

    @Mock
    private ServerWebExchange exchange;
    @Mock
    private WebFilterChain chain;
    @Mock
    private org.springframework.http.server.reactive.ServerHttpResponse response;

    private CorsConfig corsConfig;
    private SecurityHeadersConfig securityHeadersConfig;

    @BeforeEach
    void setUp() {
        corsConfig = new CorsConfig();
        securityHeadersConfig = new SecurityHeadersConfig();
    }

    @Test
    void testCorsConfigCreation() {
        assertNotNull(corsConfig);
    }

    @Test
    void testCorsWebFilterBean() {
        // Arrange
        String origins = "http://localhost:3000,http://localhost:8080";

        // Act
        CorsWebFilter corsWebFilter = corsConfig.corsWebFilter(origins);

        // Assert
        assertNotNull(corsWebFilter);
    }

    @Test
    void testCorsWebFilterWithSingleOrigin() {
        // Arrange
        String origins = "http://localhost:3000";

        // Act
        CorsWebFilter corsWebFilter = corsConfig.corsWebFilter(origins);

        // Assert
        assertNotNull(corsWebFilter);
    }

    @Test
    void testSecurityHeadersConfigCreation() {
        assertNotNull(securityHeadersConfig);
    }

    @Test
    void testSecurityHeadersFilter() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(headers);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = securityHeadersConfig.filter(exchange, chain);

        // Assert
        StepVerifier.create(result).verifyComplete();

        assertEquals("default-src 'self'; frame-ancestors 'self'; form-action 'self'",
                headers.getFirst("Content-Security-Policy"));
        assertEquals("max-age=31536000;", headers.getFirst("Strict-Transport-Security"));
        assertEquals("nosniff", headers.getFirst("X-Content-Type-Options"));
        assertEquals("", headers.getFirst("Server"));
        assertEquals("no-store", headers.getFirst("Cache-Control"));
        assertEquals("no-cache", headers.getFirst("Pragma"));
        assertEquals("strict-origin-when-cross-origin", headers.getFirst("Referrer-Policy"));
    }

    @Test
    void testSecurityHeadersFilterExecution() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(headers);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        // Act
        securityHeadersConfig.filter(exchange, chain).block();

        // Assert
        assertFalse(headers.isEmpty());
        assertEquals(7, headers.size());
    }
}
