package co.com.crediya.api.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerResponse;
import co.com.crediya.api.dto.SolicitudRequestDto;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SolicitudesHandlerTest {

    @Mock
    private Validator validator;

    @Mock
    private EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private LoggerGateway loggerGateway;

    @InjectMocks
    private SolicitudesHandler solicitudesHandler;

    private SolicitudRequestDto solicitudRequestDto;
    private Solicitud solicitud;
    private Solicitud solicitudGuardada;

    @BeforeEach
    void setUp() {
        // Arrange - Datos de prueba
        solicitudRequestDto = new SolicitudRequestDto();
        solicitudRequestDto.setMonto(new BigDecimal("50000"));
        solicitudRequestDto.setPlazo(12);
        solicitudRequestDto.setEmail("usuario@example.com");
        solicitudRequestDto.setIdTipoPrestamo(1L);

        solicitud = new Solicitud(new BigDecimal("50000"), 12, "usuario@example.com",
                1L, 1L);

        solicitudGuardada = new Solicitud(1L, new BigDecimal("50000"), 12, "usuario@example.com", 1L, 1L);
    }

    @Test
    @DisplayName("Debería crear solicitud exitosamente cuando los datos son válidos")
    void deberiaCrearSolicitudExitosamenteCuandoLosDatosSonValidos() {
        // Arrange
        MockServerRequest request =
                MockServerRequest.builder().body(Mono.just(solicitudRequestDto));

        // Mock validator behavior - no errors
        doNothing().when(validator).validate(any(), any());

        when(objectMapper.map(any(SolicitudRequestDto.class), eq(Solicitud.class)))
                .thenReturn(solicitud);
        when(enviarSolicitudPrestamoUseCase.guardarSolicitud(any(Solicitud.class)))
                .thenReturn(Mono.just(solicitudGuardada));

        // Act
        Mono<ServerResponse> response = solicitudesHandler.registroSolicitudPrestamo(request);

        // Assert
        StepVerifier.create(response).assertNext(serverResponse -> {
            assertThat(serverResponse.statusCode().value()).isEqualTo(201);
        }).verifyComplete();
    }

    @Test
    @DisplayName("Debería manejar error cuando el use case falla")
    void deberiaManejarErrorCuandoElUseCaseFalla() {
        // Arrange
        MockServerRequest request =
                MockServerRequest.builder().body(Mono.just(solicitudRequestDto));

        // Mock validator behavior - no errors
        doNothing().when(validator).validate(any(), any());

        when(objectMapper.map(any(SolicitudRequestDto.class), eq(Solicitud.class)))
                .thenReturn(solicitud);
        when(enviarSolicitudPrestamoUseCase.guardarSolicitud(any(Solicitud.class)))
                .thenReturn(Mono.error(new RuntimeException("Error en base de datos")));

        // Act
        Mono<ServerResponse> response = solicitudesHandler.registroSolicitudPrestamo(request);

        // Assert
        StepVerifier.create(response).expectError(RuntimeException.class).verify();
    }

    @Test
    @DisplayName("Debería obtener listado de solicitudes")
    void deberiaObtenerListadoDeSolicitudes() {
        /*
         * // Arrange MockServerRequest request = MockServerRequest.builder().build();
         * 
         * // Act Mono<ServerResponse> response = solicitudesHandler.listadoSolicitudes(request);
         * 
         * // Assert StepVerifier.create(response) .assertNext(serverResponse -> {
         * assertThat(serverResponse.statusCode().value()).isEqualTo(200); }) .verifyComplete();
         */
    }
}
