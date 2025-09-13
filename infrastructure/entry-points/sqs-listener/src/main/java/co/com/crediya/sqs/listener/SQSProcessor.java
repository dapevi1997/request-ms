package co.com.crediya.sqs.listener;

import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.exceptions.DomainException;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.sqs.listener.helper.BodyMensajeColaActualizarDto;
import co.com.crediya.sqs.listener.util.Constantes;
import co.com.crediya.usecase.actualizarsolicitud.ActualizarSolicitudUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.function.Function;

@Service
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final ActualizarSolicitudUseCase actualizarSolicitudUseCase;
    private final EstadosRepository estadosRepository;
    private final ObjectMapper objectMapper;

    public SQSProcessor(ActualizarSolicitudUseCase actualizarSolicitudUseCase, EstadosRepository estadosRepository) {
        this.actualizarSolicitudUseCase = actualizarSolicitudUseCase;
        this.estadosRepository = estadosRepository;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Mono<Void> apply(Message message) {

        BodyMensajeColaActualizarDto dto = null;
        try {
            dto = objectMapper.readValue(message.body(), BodyMensajeColaActualizarDto.class);
        } catch (JsonProcessingException e) {
            throw new DomainException("Error parseando mensaje entrante: " + message.body());
        }

        Solicitud solicitud = new Solicitud(
                dto.getIdSolicitud(),
                dto.getMonto(),
                dto.getPlazo(),
                dto.getEmail(),
                dto.getIdEstado(),
                dto.getIdTipoPrestamo()
        );
        solicitud.setFechaCreacion(dto.getFechaCreacion());

        return estadosRepository.findByNombre(dto.getEstadoSolicitud())
                .switchIfEmpty(Mono.error(new DomainException(Constantes.MensajesError.ESTADO_NO_ENCONTRADO)))
                .flatMap(estado -> {
                    solicitud.setIdEstado(estado.getIdEstado());
                    return actualizarSolicitudUseCase.actualizarSolicitud(solicitud);
                })
                .then();
    }
}
