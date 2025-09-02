package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.entity.EstadosEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EstadosReactiveRepository extends ReactiveCrudRepository<EstadosEntity, Long>, ReactiveQueryByExampleExecutor<EstadosEntity> {
    Mono<EstadosEntity> findByNombre(String nombre);
}
