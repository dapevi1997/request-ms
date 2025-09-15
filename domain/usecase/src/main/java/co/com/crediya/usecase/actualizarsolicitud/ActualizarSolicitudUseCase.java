package co.com.crediya.usecase.actualizarsolicitud;

import co.com.crediya.model.estado.Estado;
import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.exceptions.DomainException;
import co.com.crediya.model.mensajesender.JsonMapperGateway;
import co.com.crediya.model.mensajesender.MensajeSenderGateway;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.model.utils.Constantes;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import static co.com.crediya.model.utils.Constantes.ColasSqs;

public class ActualizarSolicitudUseCase {
    private final SolicitudRepository solicitudRepository;
    private final EstadosRepository estadosRepository;
    private final MensajeSenderGateway mensajeSenderGateway;
    private final JsonMapperGateway jsonMapperGateway;

    public ActualizarSolicitudUseCase(SolicitudRepository solicitudRepository, EstadosRepository estadosRepository, MensajeSenderGateway mensajeSenderGateway, JsonMapperGateway jsonMapperGateway) {
        this.solicitudRepository = solicitudRepository;
        this.estadosRepository = estadosRepository;
        this.mensajeSenderGateway = mensajeSenderGateway;
        this.jsonMapperGateway = jsonMapperGateway;
    }

    public Mono<Solicitud> actualizarSolicitud(Solicitud solicitud) {
        return solicitudRepository.findById(solicitud.getIdSolicitud())
                .flatMap(solic ->
                        estadosRepository.findById(solicitud.getIdEstado())
                                .switchIfEmpty(Mono.error(new DomainException(
                                        Constantes.MENSAJE_ESTADO_NO_ENCONTRADO + solic.getIdEstado())))
                                .map(estado -> Tuples.of(solic, estado))
                )
                .flatMap(tuple -> {
                    Solicitud solicBd = tuple.getT1();
                    Estado estado = tuple.getT2();
                    solicBd.setIdEstado(solicitud.getIdEstado());

                    if (!Constantes.ESTADO_APROBADO.equals(estado.getNombre())) {
                        return solicitudRepository.save(solicBd);
                    }

                    final String payload;
                    try {
                        payload = jsonMapperGateway.objetoAJsonString(solicBd);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }

                    return solicitudRepository.save(solicBd)
                            .flatMap(saved ->
                                    mensajeSenderGateway.send(ColasSqs.COLA_APROBADOS, payload)
                                            .thenReturn(saved)
                            );
                });
    }
}
