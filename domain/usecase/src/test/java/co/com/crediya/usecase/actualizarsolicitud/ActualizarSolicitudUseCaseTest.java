package co.com.crediya.usecase.actualizarsolicitud;

import co.com.crediya.model.estado.Estado;
import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.exceptions.JsonMapperException;
import co.com.crediya.model.mensajesender.JsonMapperGateway;
import co.com.crediya.model.mensajesender.MensajeSenderGateway;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActualizarSolicitudUseCaseTest {
    @InjectMocks
    private ActualizarSolicitudUseCase actualizarSolicitudUseCase;

    @Mock
    private EstadosRepository estadosRepository;

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private MensajeSenderGateway mensajeSenderGateway;

    @Mock
    private JsonMapperGateway jsonMapperGateway;

    @Test
    void actualizarSolicitudTest() throws JsonMapperException {
        // Arrange
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(1L);
        solicitud.setEmail("nuevo@email.com");
        solicitud.setIdEstado(2L);

        Estado estado = new Estado();
        estado.setIdEstado(2L);
        estado.setNombre("APROBADO");

        when(solicitudRepository.findById(anyLong())).thenReturn(Mono.just(solicitud));
        when(estadosRepository.findById(anyLong())).thenReturn(Mono.just(estado));
        when(solicitudRepository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(mensajeSenderGateway.send(anyString(),anyString())).thenReturn(Mono.just("{}"));
        when(jsonMapperGateway.objetoAJsonString(any())).thenReturn("{}");

        // Act & Assert
        StepVerifier.create(actualizarSolicitudUseCase.actualizarSolicitud(solicitud))
                .expectNextMatches(s ->
                        s.getEmail().equals("nuevo@email.com") &&
                                s.getIdEstado() == 2L
                )
                .verifyComplete();
    }

    @Test
    void actualizarSolicitudJsonExceptionTest() throws JsonMapperException {
        // Arrange
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(1L);
        solicitud.setEmail("nuevo@email.com");
        solicitud.setIdEstado(2L);

        Estado estado = new Estado();
        estado.setIdEstado(2L);
        estado.setNombre("APROBADO");

        when(solicitudRepository.findById(anyLong())).thenReturn(Mono.just(solicitud));
        when(estadosRepository.findById(anyLong())).thenReturn(Mono.just(estado));
        when(jsonMapperGateway.objetoAJsonString(any())).thenThrow(new JsonMapperException("test"));

        // Act & Assert
        StepVerifier.create(actualizarSolicitudUseCase.actualizarSolicitud(solicitud))
                .expectError(JsonMapperException.class)
                .verify();

        verify(solicitudRepository, never()).save(any());
        verify(jsonMapperGateway).objetoAJsonString(any());
    }

    @Test
    void actualizarSolicitudRechazadoTest() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(1L);
        solicitud.setEmail("nuevo@email.com");
        solicitud.setIdEstado(2L);

        Estado estado = new Estado();
        estado.setIdEstado(2L);
        estado.setNombre("RECHAZADO");

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