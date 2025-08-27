package co.com.crediya.model.tipoprestamo.gateways;

import co.com.crediya.model.tipoprestamo.TipoPrestamo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TipoPrestamoRepository {
    Flux<TipoPrestamo> findAll();
    Mono<TipoPrestamo> findById(Long id);
    Mono<Boolean> existsById(Long id);
}
