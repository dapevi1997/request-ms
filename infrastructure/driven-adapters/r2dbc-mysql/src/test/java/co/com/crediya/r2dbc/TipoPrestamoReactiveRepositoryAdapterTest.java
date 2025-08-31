package co.com.crediya.r2dbc;

import co.com.crediya.model.tipoprestamo.TipoPrestamo;
import co.com.crediya.r2dbc.entity.TipoPrestamoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoPrestamoReactiveRepositoryAdapterTest {
    @InjectMocks
    private TipoPrestamoReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    private TipoPrestamoReactiveRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private TransactionalOperator transactionalOperator;

    @Test
    @DisplayName("Debería encontrar tipo de préstamo por id exitosamente")
    void deberiaEncontrarSiExistePorNombreExitosamente() {
        // Arrange
        Long id = 1L;

        TipoPrestamoEntity tipoPrestamoEntity = new TipoPrestamoEntity();
        TipoPrestamo tipoPrestamo  = new TipoPrestamo();

        when(repository.existsById(id)).thenReturn(Mono.just(Boolean.TRUE));
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Mono<Boolean> result = repositoryAdapter.existsById(id);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(existe -> existe.equals(Boolean.TRUE))
                .verifyComplete();

        verify(repository).existsById(id);
    }

}