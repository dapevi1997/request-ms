package co.com.crediya.api.handler;

import co.com.crediya.api.dto.*;
import co.com.crediya.api.exceptions.BadRequestException;
import co.com.crediya.api.util.JsonMapper;
import co.com.crediya.consumer.RestConsumer;
import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.exceptions.DomainException;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.sqs.sender.SQSSender;
import co.com.crediya.usecase.actualizarsolicitud.ActualizarSolicitudUseCase;
import co.com.crediya.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import co.com.crediya.usecase.obtenerlistadorevisionmanual.ObtenerListadoRevisionManualUseCase;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SolicitudesHandler {
    private final Validator validator;
    private final ObjectMapper objectMapper;
    private final EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase;
    private final ObtenerListadoRevisionManualUseCase obtenerListadoRevisionManualUseCase;
    private final LoggerGateway loggerGateway;
    private final RestConsumer restConsumer;
    private final ActualizarSolicitudUseCase actualizarSolicitudUseCase;
    private final SQSSender sqsSender;
    private final JsonMapper jsonMapper;
    private final EstadosRepository estadosRepository;


    public Mono<ServerResponse> registroSolicitudPrestamo(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SolicitudRequestDto.class)
                .doOnError(error -> loggerGateway.error("Error al leer el cuerpo de la solicitud: {}", error.getMessage()))
                .flatMap(this::validateRequestsDtos)
                .map(solicitudRequestDto -> objectMapper.map(solicitudRequestDto, Solicitud.class))
                .flatMap(enviarSolicitudPrestamoUseCase::guardarSolicitud)
                .flatMap(solicitud -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(SolicitudResponseDto
                                .builder()
                                .idPrestamo(solicitud.getIdTipoPrestamo())
                                .email(solicitud.getEmail())
                                .mensaje("Solicitud creada correctamente").build()));
    }

    private <T> Mono<T> validateRequestsDtos(T request) {
        return Mono.fromCallable(() -> {
            Errors errors = new BeanPropertyBindingResult(request, request.getClass().getSimpleName());
            validator.validate(request, errors);

            if (errors.hasErrors()) {
                String mensajesError = errors.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", "));
                loggerGateway.error("La request recibida tiene los siquientes errores: {}", mensajesError);
                throw new BadRequestException("Errores de validación: " + mensajesError);
            }

            return request;
        });
    }

    public Mono<ServerResponse> listadoSolicitudes(ServerRequest serverRequest) {
        String estado = serverRequest.queryParam("estado").orElse("PENDIENTE_DE_REVISION");
        int limit = serverRequest.queryParam("limit").map(Integer::parseInt).orElse(10);
        int offset = serverRequest.queryParam("offset").map(Integer::parseInt).orElse(0);
        String token = serverRequest.headers().firstHeader("Authorization");

        return obtenerListadoRevisionManualUseCase
                .listSolicitudesAprobadasUltimoMes(estado, limit, offset)
                .flatMapSequential(solicitud ->
                        restConsumer.getUserByEmail(solicitud.getEmail(), token)
                                .map(user -> SolicitudListaPendientesRevisionResponseDto.builder()
                                        .email(solicitud.getEmail())
                                        .monto(solicitud.getMonto())
                                        .plazo(solicitud.getPlazo())
                                        .tipoPrestamo(solicitud.getTipoPrestamo())
                                        .totalMontoAprobadoUltimoMes(solicitud.getTotalMontoAprobadoUltimoMes())
                                        .estado(solicitud.getEstado())
                                        .tasaInteres(solicitud.getTasaInteres())
                                        .nombreUsuario(user.getName())
                                        .salarioBase(user.getBaseSalary())
                                        .build()
                                )
                )
                .collectList()
                .flatMap(list ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(list)
                );
    }

    public Mono<ServerResponse> actualizarSolicitud(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ActualizarSolicitudRequestDto.class)
                .flatMap(this::validateRequestsDtos)
                .doOnError(error -> loggerGateway.error("Error al leer el cuerpo de la solicitud: {}", error.getMessage()))
                .map(request -> objectMapper.map(request, Solicitud.class))
                .flatMap(actualizarSolicitudUseCase::actualizarSolicitud)
                .doOnSuccess(solicitud -> loggerGateway.info("Solicitud {} actualizada en base de datos", solicitud.getIdSolicitud()))
                .filter(solicitud -> solicitud.getIdEstado().equals(3L) || solicitud.getIdEstado().equals(4L))
                .flatMap(solicitud -> estadosRepository.findById(solicitud.getIdEstado())
                        .switchIfEmpty(Mono.error(new DomainException("No existe estados en base de datos con id " + solicitud.getIdEstado())))
                                .flatMap(estado -> Mono.fromCallable(() -> jsonMapper.convertirObjetoAJson(NotificacionEstadoSqsDto.builder()
                                        .mensaje(estado.getDescripcion())
                                        .estado(estado.getNombre())
                                        .correo(solicitud.getEmail()).build())))
                        .flatMap(sqsSender::send)
                        .doOnSuccess(s -> loggerGateway.info("Mensaje enviado a cola SQS, id préstamo {}", solicitud.getIdSolicitud()))
                        .then(Mono.just(solicitud)))
                .flatMap(solicitud -> ServerResponse.ok().bodyValue(ActualizarSolicitudResponseDto.builder()
                        .idPrestamo(solicitud.getIdTipoPrestamo())
                        .email(solicitud.getEmail())
                        .mensaje("Solicitud actualizada exitosamente")
                        .build()));
    }
}
