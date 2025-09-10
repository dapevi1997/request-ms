package co.com.crediya.sqs.sender;

import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.mensajesender.MensajeSenderGateway;
import co.com.crediya.sqs.sender.config.SQSSenderProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.Map;

@Service
public class SQSSender implements MensajeSenderGateway {
    private final LoggerGateway loggerGateway;
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final Map<String, String> queues;

    public SQSSender(LoggerGateway loggerGateway, SqsAsyncClient client, SQSSenderProperties properties) {
        this.loggerGateway = loggerGateway;
        this.client = client;
        this.properties = properties;
        this.queues = Map.of(
                "colaNotificacionEstado", properties.queueUrl(),
                "colaCapacidadEndeudamiento", properties.queueCapacidadUrl()
        );
    }

    @Override
    public Mono<String> send(String queueName, String message) {
        String queueUrl = queues.get(queueName);
        if (queueUrl == null) {
            return Mono.error(new IllegalArgumentException("Queue no configurada: " + queueName));
        }

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();

        return Mono.fromFuture(client.sendMessage(request))
                .doOnNext(response -> loggerGateway.info("Message sent to {} -> {}", queueName, response.messageId()))
                .map(SendMessageResponse::messageId);
    }
}
