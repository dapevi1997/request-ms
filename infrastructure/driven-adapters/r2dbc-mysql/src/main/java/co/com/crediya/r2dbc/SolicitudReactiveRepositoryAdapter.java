package co.com.crediya.r2dbc;

import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.SolicitudConTotalAprobadoUltimoMes;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.r2dbc.entity.SolicitudEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;

@Repository
public class SolicitudReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Solicitud,
        SolicitudEntity,
    Long,
        SolicitudReactiveRepository
> implements SolicitudRepository {
    private final TransactionalOperator transactionalOperator;
    public SolicitudReactiveRepositoryAdapter(SolicitudReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Solicitud.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Flux<SolicitudConTotalAprobadoUltimoMes> findByEstadoFilter(String estado, int limit, int offset) {
        return repository.findByEstadoFilter(estado, limit, offset)
                .map(solicitudPendienteDto -> mapper.map(solicitudPendienteDto, SolicitudConTotalAprobadoUltimoMes.class))
                .as(transactionalOperator::transactional);
    }
}
