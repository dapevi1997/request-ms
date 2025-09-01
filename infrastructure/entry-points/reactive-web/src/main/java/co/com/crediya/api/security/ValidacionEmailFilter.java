package co.com.crediya.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import co.com.crediya.api.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import com.fasterxml.jackson.databind.JsonNode;

import static co.com.crediya.api.util.Constantes.URL_SOLICITAR_CREDITO;

@Component
public class ValidacionEmailFilter implements WebFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!exchange.getRequest().getMethod().equals(HttpMethod.POST)
                || !exchange.getRequest().getURI().getPath().contains(URL_SOLICITAR_CREDITO)) {
            return chain.filter(exchange);
        }

        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> cacheAndValidate(exchange, chain, securityContext));
    }

    private Mono<Void> cacheAndValidate(ServerWebExchange exchange, WebFilterChain chain, SecurityContext context) {
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    String bodyString = new String(bytes, StandardCharsets.UTF_8);

                    try {
                        JsonNode jsonNode = objectMapper.readTree(bodyString);
                        String emailFromRequest = jsonNode.has("email") ? jsonNode.get("email").asText() : null;
                        String emailFromToken = context.getAuthentication().getName();

                        if (emailFromRequest == null || !emailFromRequest.equalsIgnoreCase(emailFromToken)) {
                            return forbiddenResponse(exchange);
                        }

                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                            return Mono.just(buffer);
                        });

                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(mutatedRequest)
                                .build();

                        return chain.filter(mutatedExchange);

                    } catch (Exception e) {
                        return forbiddenResponse(exchange);
                    }
                });
    }

    private Mono<Void> forbiddenResponse(ServerWebExchange exchange) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .httpStatus(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("El cliente solo puede crear solicitudes de préstamo a nombre propio")
                .build();

        try {
            byte[] bytes = objectMapper.writeValueAsString(error).getBytes(StandardCharsets.UTF_8);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory()
                    .wrap(bytes)));
        } catch (Exception ex) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
    }
}
