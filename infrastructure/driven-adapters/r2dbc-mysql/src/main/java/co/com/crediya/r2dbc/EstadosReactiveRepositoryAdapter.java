package co.com.crediya.r2dbc;

import co.com.crediya.model.estados.Estados;
import co.com.crediya.model.estados.gateways.EstadosRepository;
import co.com.crediya.r2dbc.entity.EstadosEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EstadosReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Estados,
        EstadosEntity,
    Long,
        EstadosReactiveRepository
> implements EstadosRepository {
    public EstadosReactiveRepositoryAdapter(EstadosReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Estados.class));
    }

}
