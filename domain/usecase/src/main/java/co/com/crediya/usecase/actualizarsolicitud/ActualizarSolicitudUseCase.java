package co.com.crediya.usecase.actualizarsolicitud;

import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.exceptions.DomainException;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import reactor.core.publisher.Mono;

public class ActualizarSolicitudUseCase {
    private final SolicitudRepository solicitudRepository;
    private final EstadosRepository estadosRepository;

    public ActualizarSolicitudUseCase(SolicitudRepository solicitudRepository, EstadosRepository estadosRepository) {
        this.solicitudRepository = solicitudRepository;
        this.estadosRepository = estadosRepository;
    }

    public Mono<Solicitud> actualizarSolicitud(Solicitud solicitud) {
        return Mono.just(solicitud)
                .flatMap(soli -> solicitudRepository.findById(soli.getIdSolicitud()))
                .flatMap(solic -> estadosRepository.findById(solic.getIdEstado())
                        .switchIfEmpty(Mono.error(new DomainException("No se encuentra estado con el id proporcionado")))
                        .then(Mono.just(solic)))
                .flatMap(solicitud2 -> {
                    solicitud2.setEmail(solicitud.getEmail());
                    solicitud2.setIdEstado(solicitud.getIdEstado());
                    return solicitudRepository.save(solicitud2);
                });

    }
}
