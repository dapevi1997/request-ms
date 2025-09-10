package co.com.crediya.model.mensajesender;

import reactor.core.publisher.Mono;

public interface MensajeSenderGateway {
    Mono<String> send(String queueName, String message);
}
