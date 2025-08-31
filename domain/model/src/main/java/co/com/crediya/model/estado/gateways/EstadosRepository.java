package co.com.crediya.model.estado.gateways;

import co.com.crediya.model.estado.Estado;
import reactor.core.publisher.Mono;

public interface EstadosRepository {
    Mono<Estado> findById(Long id);
    Mono<Estado> findByNombre(String nombre);
}
