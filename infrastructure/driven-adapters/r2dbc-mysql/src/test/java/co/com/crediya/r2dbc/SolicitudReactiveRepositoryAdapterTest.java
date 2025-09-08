package co.com.crediya.r2dbc;

import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.SolicitudConTotalAprobadoUltimoMes;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.r2dbc.dto.SolicitudPendienteAprobacionDto;
import co.com.crediya.r2dbc.entity.SolicitudEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolicitudReactiveRepositoryAdapterTest {
    @InjectMocks
    SolicitudReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    SolicitudReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Mock
    TransactionalOperator transactionalOperator;

    @Test
    void mustFindValueById() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(1L);
        solicitud.setEmail("mail@mail.com");
        SolicitudEntity solicitudEntity = new SolicitudEntity();
        solicitudEntity.setEmail("mail@mail.com");
        when(repository.findById(1L)).thenReturn(Mono.just(solicitudEntity));
        when(mapper.map(solicitudEntity, Solicitud.class)).thenReturn(solicitud);

        // Act
        Mono<Solicitud> result = repositoryAdapter.findById(1L);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(solicitud1 -> solicitud1.getEmail().equals("mail@mail.com"))
                .verifyComplete();
    }

    @Test
    void save() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(1L);
        solicitud.setEmail("mail@mail.com");
        SolicitudEntity solicitudEntity = new SolicitudEntity();
        solicitudEntity.setEmail("mail@mail.com");
        when(repository.save(solicitudEntity)).thenReturn(Mono.just(solicitudEntity));
        when(mapper.map(solicitud, SolicitudEntity.class)).thenReturn(solicitudEntity);
        when(mapper.map(solicitudEntity, Solicitud.class)).thenReturn(solicitud);

        // Act
        Mono<Solicitud> result = repositoryAdapter.save(solicitud);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(solicitud1 -> solicitud1.getEmail().equals("mail@mail.com"))
                .verifyComplete();
    }

    @Test
    void testFindByEstadoFilter() {
        // Arrange
        String estado = "APROBADO";
        int limit = 10;
        int offset = 0;

        SolicitudPendienteAprobacionDto dto = new SolicitudPendienteAprobacionDto();
        SolicitudConTotalAprobadoUltimoMes mapped = new SolicitudConTotalAprobadoUltimoMes();

        when(repository.findByEstadoFilter(estado, limit, offset))
                .thenReturn(Flux.just(dto));
        when(mapper.map(any(SolicitudPendienteAprobacionDto.class), eq(SolicitudConTotalAprobadoUltimoMes.class)))
                .thenReturn(mapped);
        when(transactionalOperator.transactional(any(Flux.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Flux<SolicitudConTotalAprobadoUltimoMes> result = repositoryAdapter.findByEstadoFilter(estado, limit, offset);

        // Act and Assert
        StepVerifier.create(result)
                .expectNext(mapped)
                .verifyComplete();
    }
}
