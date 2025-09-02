package co.com.crediya.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

class SecurityHeadersConfigTest {

    private SecurityHeadersConfig securityHeadersConfig;
    private MockServerWebExchange exchange;
    private WebFilterChain webFilterChain;

    SecurityHeadersConfigTest() {
    }

    @BeforeEach
    void setUp() {
        securityHeadersConfig = new SecurityHeadersConfig();
        MockServerHttpRequest request = MockServerHttpRequest.get("/test").build();
        exchange = MockServerWebExchange.from(request);
        webFilterChain = filterExchange -> Mono.empty();
    }

    @Test
    @DisplayName("Debería agregar header Content-Security-Policy")
    void deberiaAgregarHeaderContentSecurityPolicy() {
        // Act
        securityHeadersConfig.filter(exchange, webFilterChain).block();

        // Assert
        HttpHeaders headers = exchange.getResponse().getHeaders();
        String csp = headers.getFirst("Content-Security-Policy");
        assert csp != null;
        assert csp.equals("default-src 'self'; frame-ancestors 'self'; form-action 'self'");
    }

    @Test
    @DisplayName("Debería agregar header Strict-Transport-Security")
    void deberiaAgregarHeaderStrictTransportSecurity() {
        // Act
        securityHeadersConfig.filter(exchange, webFilterChain).block();

        // Assert
        String hsts = exchange.getResponse().getHeaders().getFirst("Strict-Transport-Security");
        assert hsts != null;
        assert hsts.equals("max-age=31536000;");
    }

    @Test
    @DisplayName("Debería agregar header X-Content-Type-Options")
    void deberiaAgregarHeaderXContentTypeOptions() {
        // Act
        securityHeadersConfig.filter(exchange, webFilterChain).block();

        // Assert
        String contentType = exchange.getResponse().getHeaders().getFirst("X-Content-Type-Options");
        assert contentType != null;
        assert contentType.equals("nosniff");
    }

    @Test
    @DisplayName("Debería ocultar información del servidor")
    void deberiaOcultarInformacionDelServidor() {
        // Act
        securityHeadersConfig.filter(exchange, webFilterChain).block();

        // Assert
        String server = exchange.getResponse().getHeaders().getFirst("Server");
        assert server != null;
        assert server.isEmpty();
    }

    @Test
    @DisplayName("Debería configurar headers de cache")
    void deberiaConfigurarHeadersDeCache() {
        // Act
        securityHeadersConfig.filter(exchange, webFilterChain).block();

        // Assert
        HttpHeaders headers = exchange.getResponse().getHeaders();
        assert "no-store".equals(headers.getFirst("Cache-Control"));
        assert "no-cache".equals(headers.getFirst("Pragma"));
    }

    @Test
    @DisplayName("Debería configurar Referrer-Policy")
    void deberiaConfigurarReferrerPolicy() {
        // Act
        securityHeadersConfig.filter(exchange, webFilterChain).block();

        // Assert
        String referrerPolicy = exchange.getResponse().getHeaders().getFirst("Referrer-Policy");
        assert referrerPolicy != null;
        assert referrerPolicy.equals("strict-origin-when-cross-origin");
 
   }
}