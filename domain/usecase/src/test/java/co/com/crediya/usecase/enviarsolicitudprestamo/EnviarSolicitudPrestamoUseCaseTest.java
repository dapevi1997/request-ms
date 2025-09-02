package co.com.crediya.usecase.enviarsolicitudprestamo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;

import co.com.crediya.model.logger.LoggerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import co.com.crediya.model.estado.Estado;
import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.exceptions.DomainException;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.model.tipoprestamo.gateways.TipoPrestamoRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class EnviarSolicitudPrestamoUseCaseTest {

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private TipoPrestamoRepository tipoPrestamoRepository;

    @Mock
    private LoggerGateway loggerGateway;

    @Mock
    private EstadosRepository estadosRepository;

    private EnviarSolicitudPrestamoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new EnviarSolicitudPrestamoUseCase(solicitudRepository, tipoPrestamoRepository,
                estadosRepository, loggerGateway);
    }

    @Test
    @DisplayName("Debería guardar solicitud exitosamente cuando todo es válido")
    void deberiaGuardarSolicitudExitosamenteCuandoTodoEsValido() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        solicitud.setMonto(new BigDecimal("50000"));
        solicitud.setPlazo(12);
        solicitud.setEmail("user@mail.com");
        solicitud.setDocumentoIdentidad("12345678");
        solicitud.setIdTipoPrestamo(1L);

        Estado estadoPendiente = new Estado(2L, "PENDIENTE_DE_REVISION", "Estado inicial");
        Solicitud solicitudGuardada = new Solicitud(1L, new BigDecimal("50000"), 12,
                "usuario@example.com", "12345678", 2L, 1L);

        when(tipoPrestamoRepository.existsById(anyLong())).thenReturn(Mono.just(true));
        when(estadosRepository.findByNombre(anyString())).thenReturn(Mono.just(estadoPendiente));
        when(solicitudRepository.save(any(Solicitud.class)))
                .thenReturn(Mono.just(solicitudGuardada));

        // Act & Assert
        StepVerifier.create(useCase.guardarSolicitud(solicitud)).expectNext(solicitudGuardada)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando tipo de préstamo no existe")
    void deberiaLanzarExcepcionCuandoTipoPrestamoNoExiste() {
        // Arrange
        Solicitud solicitud = new Solicitud(new BigDecimal("50000"), 12, "usuario@example.com",
                "12345678", 1L, 999L // ID que no existe
        );

        when(tipoPrestamoRepository.existsById(anyLong())).thenReturn(Mono.just(false));

        // Act & Assert
        StepVerifier.create(useCase.guardarSolicitud(solicitud))
                .expectErrorMatches(throwable -> throwable instanceof DomainException && throwable
                        .getMessage().contains("El tipo de préstamo con Id 999 no existe"))
                .verify();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando estado inicial no existe")
    void deberiaLanzarExcepcionCuandoEstadoInicialNoExiste() {
        // Arrange
        Solicitud solicitud = new Solicitud(new BigDecimal("50000"), 12, "usuario@example.com",
                "12345678", 1L, 1L);

        when(tipoPrestamoRepository.existsById(anyLong())).thenReturn(Mono.just(true));
        when(estadosRepository.findByNombre(anyString())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(useCase.guardarSolicitud(solicitud))
                .expectErrorMatches(throwable -> throwable instanceof DomainException
                        && throwable.getMessage().contains(
                                "Estado PENDIENTE_DE_REVISION no se encuentra en la base de datos"))
                .verify();
    }
}
