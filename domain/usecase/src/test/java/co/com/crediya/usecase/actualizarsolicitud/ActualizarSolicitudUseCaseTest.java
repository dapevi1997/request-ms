package co.com.crediya.usecase.actualizarsolicitud;

import co.com.crediya.model.estado.Estado;
import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActualizarSolicitudUseCaseTest {
    @InjectMocks
    private ActualizarSolicitudUseCase actualizarSolicitudUseCase;

    @Mock
    private EstadosRepository estadosRepository;

    @Mock
    private SolicitudRepository solicitudRepository;

    @Test
    void actualizarSolicitudTest() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(1L);
        solicitud.setEmail("nuevo@email.com");
        solicitud.setIdEstado(2L);

        Estado estado = new Estado();
        estado.setIdEstado(2L);

        when(solicitudRepository.findById(anyLong())).thenReturn(Mono.just(solicitud));
        when(estadosRepository.findById(anyLong())).thenReturn(Mono.just(estado));
        when(solicitudRepository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act & Assert
        StepVerifier.create(actualizarSolicitudUseCase.actualizarSolicitud(solicitud))
                .expectNextMatches(s ->
                        s.getEmail().equals("nuevo@email.com") &&
                                s.getIdEstado() == 2L
                )
                .verifyComplete();
    }

}