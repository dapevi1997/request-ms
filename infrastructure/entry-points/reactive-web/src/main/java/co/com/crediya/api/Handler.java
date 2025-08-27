package co.com.crediya.api;

import co.com.crediya.api.dto.SolicitudRequestDto;
import co.com.crediya.api.dto.SolicitudResponseDto;
import co.com.crediya.api.exceptions.BadRequestException;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class Handler {
    private final Validator validator;
    private final ObjectMapper objectMapper;
    private  final EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase;

    public Handler(Validator validator, ObjectMapper objectMapper, EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase) {
        this.validator = validator;
        this.objectMapper = objectMapper;
        this.enviarSolicitudPrestamoUseCase = enviarSolicitudPrestamoUseCase;
    }

    public Mono<ServerResponse> registroSolicitudPrestamo(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SolicitudRequestDto.class)
                .doOnError(error -> log.error("Error al leer el cuerpo de la solicitud: {}", error.getMessage()))
                .flatMap(this::validateRequest)
                .map(solicitudRequestDto -> objectMapper.map(solicitudRequestDto, Solicitud.class))
                .flatMap(enviarSolicitudPrestamoUseCase::guardarSolicitud)
                .flatMap(solicitud -> {
                    SolicitudResponseDto solicitudResponseDto = new SolicitudResponseDto();
                    solicitudResponseDto.setIdPrestamo(solicitud.getIdTipoPrestamo());
                    solicitudResponseDto.setEmail(solicitud.getEmail());
                    solicitudResponseDto.setDocumentoIdentidad(solicitud.getDocumentoIdentidad());
                    solicitudResponseDto.setMensaje("Solicitud creada correctamente");
                    log.info("Solicitud de préstamo creada correctamente");

                    return ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(solicitudResponseDto);
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
                log.error("La request recibida tiene los siquientes errores: {}", mensajesError);
                throw new BadRequestException("Errores de validación: " + mensajesError);
            }

            return solicitudRequestDto;
        });
    }
}
