package co.com.crediya.api.security;

import co.com.crediya.api.dto.ErrorResponseDto;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class ValidacionUsuarioCrearSolicitudPrestamoFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {
    private final ObjectMapper objectMapper;

    public ValidacionUsuarioCrearSolicitudPrestamoFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(ctx -> request.bodyToMono(String.class)
                        .flatMap(body -> {
                            try {
                                JsonNode json = objectMapper.readTree(body);
                                String emailFromRequest = json.path("email").asText(null);
                                String emailFromToken = ctx.getAuthentication().getName();

                                if (emailFromRequest == null || !emailFromRequest.equalsIgnoreCase(emailFromToken)) {
                                    return forbiddenResponse();
                                }

                                byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
                                DataBuffer buffer = request.exchange()
                                        .getResponse()
                                        .bufferFactory()
                                        .wrap(bytes);

                                Flux<DataBuffer> cachedFlux = Flux.just(buffer);

                                ServerHttpRequest decorated = new ServerHttpRequestDecorator(request.exchange().getRequest()) {
                                    @Override
                                    public Flux<DataBuffer> getBody() {
                                        return cachedFlux;
                                    }
                                };

                                ServerRequest mutated = ServerRequest.create(
                                        request.exchange().mutate().request(decorated).build(),
                                        request.messageReaders()
                                );

                                return next.handle(mutated);

                            } catch (Exception e) {
                                return forbiddenResponse();
                            }
                        }))
                .switchIfEmpty(next.handle(request));
    }

    private Mono<ServerResponse> forbiddenResponse() {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .httpStatus(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("El cliente solo puede crear solicitudes de préstamo a nombre propio")
                .build();

        return ServerResponse.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }
}
