package co.com.crediya.sqs.sender;

import co.com.crediya.model.exceptions.DomainException;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.mensajesender.MensajeSenderGateway;
import co.com.crediya.sqs.sender.config.SQSSenderProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import static co.com.crediya.sqs.sender.util.Constantes.MensajesError;
import co.com.crediya.model.utils.Constantes.ColasSqs;

import java.util.Map;

@Service
public class SQSSender implements MensajeSenderGateway {
    private final LoggerGateway loggerGateway;
    private final SqsAsyncClient client;
    private final Map<String, String> queues;

    public SQSSender(LoggerGateway loggerGateway, SQSSenderProperties properties, SqsAsyncClient client) {
        this.loggerGateway = loggerGateway;
        this.client = client;
        this.queues = Map.of(
                ColasSqs.COLA_NOTIFICACION_ESTADO, properties.queueUrl(),
                ColasSqs.COLA_CAPACIDAD_ENDEUDAMIENTO, properties.queueCapacidadUrl(),
                ColasSqs.COLA_APROBADOS, properties.queueAprobadosUrl()
        );
    }

    @Override
    public Mono<String> send(String queueName, String message) {
        String queueUrl = queues.get(queueName);
        if (queueUrl == null) {
            return Mono.error(new DomainException(MensajesError.COLA_NO_ENCONTRADA + queueName));
        }
        if (message.isBlank()){
            return Mono.error(new DomainException("Mensaje no válido"));
        }

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();

        return Mono.fromFuture(client.sendMessage(request))
                .doOnNext(response -> loggerGateway.info("Message sent to {} -> {}", queueName, response.messageId()))
                .doOnError(error -> loggerGateway.error("Error sending message to {}: {}", queueName, error.getMessage()))
                .map(SendMessageResponse::messageId);
    }
}
