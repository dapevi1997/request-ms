package co.com.crediya.model.solicitud.gateways;

import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.SolicitudConTotalAprobadoUltimoMes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SolicitudRepository {
    Mono<Solicitud> save(Solicitud solicitud);
    Mono<Solicitud> findById(Long id);
    Flux<SolicitudConTotalAprobadoUltimoMes> findByEstadoFilter(String estado, int limit, int offset);
}
