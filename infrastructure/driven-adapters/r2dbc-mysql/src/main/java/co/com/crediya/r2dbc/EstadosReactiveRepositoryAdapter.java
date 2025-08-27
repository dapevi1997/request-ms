package co.com.crediya.r2dbc;

import co.com.crediya.model.estado.Estado;
import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.r2dbc.entity.EstadosEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class EstadosReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Estado,
        EstadosEntity,
    Long,
        EstadosReactiveRepository
> implements EstadosRepository {
    private final TransactionalOperator transactionalOperator;
    public EstadosReactiveRepositoryAdapter(EstadosReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Estado.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Estado> findByNombre(String nombre) {
        return repository.findByNombre(nombre)
                .map(estadoEntity -> mapper.map(estadoEntity, Estado.class))
                .as(transactionalOperator::transactional);
    }
}
