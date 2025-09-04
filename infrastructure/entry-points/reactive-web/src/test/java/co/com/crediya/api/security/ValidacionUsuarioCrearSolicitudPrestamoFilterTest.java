package co.com.crediya.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class ValidacionUsuarioCrearSolicitudPrestamoFilterTest {

    private ValidacionUsuarioCrearSolicitudPrestamoFilter filter;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        filter = new ValidacionUsuarioCrearSolicitudPrestamoFilter(objectMapper);
    }

    private ServerRequest buildRequestWithBody(String body) {
        MockServerWebExchange exchange = MockServerWebExchange.from(
                org.springframework.mock.http.server.reactive.MockServerHttpRequest
                        .post("/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(body)
        );
        return ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
    }

    private HandlerFunction<ServerResponse> nextHandlerReturningOk() {
        return req -> ServerResponse.ok().bodyValue("OK");
    }

    @Test
    void cuandoEmailCoincide_deberiaPermitirContinuar() {
        // Arrange
        String body = "{ \"email\": \"user@example.com\" }";
        ServerRequest request = buildRequestWithBody(body);

        var context = ReactiveSecurityContextHolder.withAuthentication(
                new TestingAuthenticationToken("user@example.com", null)
        );

        // Act
        Mono<ServerResponse> result = filter.filter(request, nextHandlerReturningOk())
                .contextWrite(context);

        // Assert
        StepVerifier.create(result)
                .consumeNextWith(response ->
                        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK)
                )
                .verifyComplete();
    }

    @Test
    void cuandoEmailNoCoincide_deberiaRetornarForbidden() {
        // Arrange
        String body = "{ \"email\": \"otro@example.com\" }";
        ServerRequest request = buildRequestWithBody(body);

        var context = ReactiveSecurityContextHolder.withAuthentication(
                new TestingAuthenticationToken("user@example.com", null)
        );

        // Act
        Mono<ServerResponse> result = filter.filter(request, nextHandlerReturningOk())
                .contextWrite(context);

        // Assert
        StepVerifier.create(result)
                .consumeNextWith(response ->
                        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN)
                )
                .verifyComplete();
    }

    @Test
    void cuandoBodyInvalido_deberiaRetornarForbidden() {
        // Arrange: body inválido
        String body = "{ email: }";
        ServerRequest request = buildRequestWithBody(body);

        var context = ReactiveSecurityContextHolder.withAuthentication(
                new TestingAuthenticationToken("user@example.com", null)
        );

        // Act
        Mono<ServerResponse> result = filter.filter(request, nextHandlerReturningOk())
                .contextWrite(context);

        // Assert
        StepVerifier.create(result)
                .consumeNextWith(response ->
                        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN)
                )
                .verifyComplete();
    }

    @Test
    void cuandoNoHayContexto_deberiaPermitirContinuar() {
        // Arrange
        String body = "{ \"email\": \"otro@example.com\" }";
        ServerRequest request = buildRequestWithBody(body);

        // Act (sin contexto de seguridad)
        Mono<ServerResponse> result = filter.filter(request, nextHandlerReturningOk());

        // Assert
        StepVerifier.create(result)
                .consumeNextWith(response ->
                        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK)
                )
                .verifyComplete();
    }
}
