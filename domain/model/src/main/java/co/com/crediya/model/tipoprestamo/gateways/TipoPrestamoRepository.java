package co.com.crediya.model.tipoprestamo.gateways;

import co.com.crediya.model.tipoprestamo.TipoPrestamo;
import reactor.core.publisher.Flux;

public interface TipoPrestamoRepository {
    Flux<TipoPrestamo> findAll();
}
