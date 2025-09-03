package co.com.crediya.api.handler;

import co.com.crediya.api.dto.SolicitudListaPendientesRevisionResponseDto;
import co.com.crediya.api.dto.SolicitudRequestDto;
import co.com.crediya.api.dto.SolicitudResponseDto;
import co.com.crediya.api.exceptions.BadRequestException;
import co.com.crediya.consumer.RestConsumer;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.solicitud.Solicitud;
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
    private  final EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase;
    private  final ObtenerListadoRevisionManualUseCase obtenerListadoRevisionManualUseCase;
    private final LoggerGateway loggerGateway;
    private final RestConsumer restConsumer;


    public Mono<ServerResponse> registroSolicitudPrestamo(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SolicitudRequestDto.class)
                .doOnError(error -> loggerGateway.error("Error al leer el cuerpo de la solicitud: {}", error.getMessage()))
                .flatMap(this::validateRequest)
                .map(solicitudRequestDto -> objectMapper.map(solicitudRequestDto, Solicitud.class))
                .flatMap(enviarSolicitudPrestamoUseCase::guardarSolicitud)
                .flatMap(solicitud -> {
                    loggerGateway.info("Solicitud de préstamo creada correctamente");

                    return ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(SolicitudResponseDto
                                    .builder()
                                    .idPrestamo(solicitud.getIdTipoPrestamo())
                                    .email(solicitud.getEmail())
                                    .mensaje("Solicitud creada correctamente").build());
                });
    }

    private Mono<SolicitudRequestDto> validateRequest(SolicitudRequestDto solicitudRequestDto) {
        return Mono.fromCallable(() -> {
            Errors errors = new BeanPropertyBindingResult(solicitudRequestDto, "solicitudRequest");
            validator.validate(solicitudRequestDto, errors);

            if (errors.hasErrors()) {
                String mensajesError = errors.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", "));
                loggerGateway.error("La request recibida tiene los siquientes errores: {}", mensajesError);
                throw new BadRequestException("Errores de validación: " + mensajesError);
            }

            return solicitudRequestDto;
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
                .doOnNext(dto -> System.out.println("Agregando a la lista: " + dto.getEmail()))
                .collectList()
                .flatMap(list ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(list)
                );
    }
}
