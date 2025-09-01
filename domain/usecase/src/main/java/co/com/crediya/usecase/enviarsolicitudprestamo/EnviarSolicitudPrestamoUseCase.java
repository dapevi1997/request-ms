package co.com.crediya.usecase.enviarsolicitudprestamo;

import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.exceptions.DomainException;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.model.tipoprestamo.gateways.TipoPrestamoRepository;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class EnviarSolicitudPrestamoUseCase {
    private static final String ESTADO_PENDIENTE_DE_REVISION = "PENDIENTE_DE_REVISION";

    private final SolicitudRepository solicitudRepository;
    private final TipoPrestamoRepository tipoPrestamoRepository;
    private final EstadosRepository estadosRepository;
    private final LoggerGateway loggerGateway;

    public EnviarSolicitudPrestamoUseCase(SolicitudRepository solicitudRepository, TipoPrestamoRepository tipoPrestamoRepository, EstadosRepository estadosRepository, LoggerGateway loggerGateway) {
        this.solicitudRepository = solicitudRepository;
        this.tipoPrestamoRepository = tipoPrestamoRepository;
        this.estadosRepository = estadosRepository;
        this.loggerGateway = loggerGateway;
    }

    public Mono<Solicitud> guardarSolicitud(Solicitud solicitud) {
        return validarTipoPrestamo(solicitud)
                .flatMap(this::setearEstadoInicial)
                .flatMap(solicitudRepository::save)
                .doOnSuccess(success -> loggerGateway.info("Solicitud con id " + solicitud.getIdSolicitud() + " guardada correctamente"));
    }

    private Mono<Solicitud> validarTipoPrestamo(Solicitud solicitud) {
        return tipoPrestamoRepository.existsById(solicitud.getIdTipoPrestamo())
                .flatMap(existe -> {
                    if (!existe) {
                        loggerGateway.error("El tipo de préstamo con id " + solicitud.getIdTipoPrestamo() + " no existe en la base de datos");
                        return Mono.error(new DomainException(
                                "El tipo de préstamo con Id " + solicitud.getIdTipoPrestamo() + " no existe"));
                    }
                    return Mono.just(solicitud);
                });
    }

    private Mono<Solicitud> setearEstadoInicial(Solicitud solicitud){
        return estadosRepository.findByNombre(ESTADO_PENDIENTE_DE_REVISION)
                .switchIfEmpty(Mono.error(new DomainException("Estado " + ESTADO_PENDIENTE_DE_REVISION + " no se encuentra en la base de datos")))
                .doOnError(error -> loggerGateway.error("Estado " + ESTADO_PENDIENTE_DE_REVISION + " no se encuentra en la base de datos"))
                .map(estado -> {
                    solicitud.setIdEstado(estado.getIdEstado());
                    return solicitud;
                });
    }
}
