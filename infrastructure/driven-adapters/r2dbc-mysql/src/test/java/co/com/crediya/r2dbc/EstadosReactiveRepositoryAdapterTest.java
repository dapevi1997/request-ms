package co.com.crediya.r2dbc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import co.com.crediya.model.estado.Estado;
import co.com.crediya.r2dbc.entity.EstadosEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class EstadosReactiveRepositoryAdapterTest {

    @InjectMocks
    EstadosReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    EstadosReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Mock
    TransactionalOperator transactionalOperator;

    @Test
    @DisplayName("Debería encontrar estado por nombre exitosamente")
    void deberiaEncontrarEstadoPorNombreExitosamente() {
        // Arrange
        String nombreBuscado = "PENDIENTE_DE_REVISION";

        EstadosEntity estadoEntity =
                new EstadosEntity(1L, "PENDIENTE_DE_REVISION", "Estado inicial de revisión");
        Estado estadoDominio =
                new Estado(1L, "PENDIENTE_DE_REVISION", "Estado inicial de revisión");

        when(repository.findByNombre(nombreBuscado)).thenReturn(Mono.just(estadoEntity));
        when(mapper.map(estadoEntity, Estado.class)).thenReturn(estadoDominio);
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Mono<Estado> result = repositoryAdapter.findByNombre(nombreBuscado);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(estado -> estado.getIdEstado().equals(1L)
                        && estado.getNombre().equals("PENDIENTE_DE_REVISION")
                        && estado.getDescripcion().equals("Estado inicial de revisión"))
                .verifyComplete();

        verify(repository).findByNombre(nombreBuscado);
        verify(mapper).map(estadoEntity, Estado.class);
    }

    @Test
    @DisplayName("Debería retornar Mono vacío cuando estado no existe")
    void deberiaRetornarMonoVacioCuandoEstadoNoExiste() {
        // Arrange
        String nombreInexistente = "ESTADO_INEXISTENTE";

        when(repository.findByNombre(nombreInexistente)).thenReturn(Mono.empty());
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Mono<Estado> result = repositoryAdapter.findByNombre(nombreInexistente);

        // Assert
        StepVerifier.create(result).expectComplete().verify();

        verify(repository).findByNombre(nombreInexistente);
    }

    @Test
    @DisplayName("Debería propagar error cuando repository falla")
    void deberiaPropagiarErrorCuandoRepositoryFalla() {
        // Arrange
        String nombreBuscado = "PENDIENTE_DE_REVISION";
        RuntimeException error = new RuntimeException("Error de base de datos");

        when(repository.findByNombre(nombreBuscado)).thenReturn(Mono.error(error));
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Mono<Estado> result = repositoryAdapter.findByNombre(nombreBuscado);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("Error de base de datos"))
                .verify();

        verify(repository).findByNombre(nombreBuscado);
    }
}
