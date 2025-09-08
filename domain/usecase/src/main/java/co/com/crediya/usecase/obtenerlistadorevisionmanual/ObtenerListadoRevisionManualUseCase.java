package co.com.crediya.usecase.obtenerlistadorevisionmanual;

import co.com.crediya.model.solicitud.SolicitudConTotalAprobadoUltimoMes;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import reactor.core.publisher.Flux;

public class ObtenerListadoRevisionManualUseCase {
    private final SolicitudRepository solicitudRepository;

    public ObtenerListadoRevisionManualUseCase(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public Flux<SolicitudConTotalAprobadoUltimoMes> listSolicitudesAprobadasUltimoMes(String estado, int limit, int offset) {
        return solicitudRepository.findByEstadoFilter(estado, limit, offset);
    }
}
