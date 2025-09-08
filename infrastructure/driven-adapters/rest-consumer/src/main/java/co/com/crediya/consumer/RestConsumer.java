package co.com.crediya.consumer;

import co.com.crediya.model.logger.LoggerGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer /* implements Gateway from domain */{
    private final WebClient client;
    private final LoggerGateway loggerGateway;

    @CircuitBreaker(name = "getUserByEmail" , fallbackMethod = "returnEmpty")
    public Mono<FindUserByEmailResponseDto> getUserByEmail(String email, String token) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/usuarios")
                        .queryParam("email", email)
                        .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(FindUserByEmailResponseDto.class)
                .switchIfEmpty(Mono.fromCallable(() -> {
                    loggerGateway.info("Usuario no encontrado con email {}", email);
                    return FindUserByEmailResponseDto.builder().build();
                }));
    }

    public Mono<String> returnEmpty(Exception error) {
        return Mono.error(error);
    }
}
