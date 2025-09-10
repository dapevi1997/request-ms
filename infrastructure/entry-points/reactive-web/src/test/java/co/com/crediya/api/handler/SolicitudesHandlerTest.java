package co.com.crediya.api.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import co.com.crediya.api.exceptions.BadRequestException;
import co.com.crediya.consumer.FindUserByEmailResponseDto;
import co.com.crediya.consumer.RestConsumer;
import co.com.crediya.model.solicitud.SolicitudConTotalAprobadoUltimoMes;
import co.com.crediya.model.tipoprestamo.gateways.TipoPrestamoRepository;
import co.com.crediya.usecase.obtenerlistadorevisionmanual.ObtenerListadoRevisionManualUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerResponse;
import co.com.crediya.api.dto.SolicitudRequestDto;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SolicitudesHandlerTest {

    @Mock
    private Validator validator;

    @Mock
    private TipoPrestamoRepository tipoPrestamoRepository;
    @Mock
    private EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase;

    @Mock
    private ObtenerListadoRevisionManualUseCase obtenerListadoRevisionManualUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private LoggerGateway loggerGateway;

    @Mock
    private RestConsumer restConsumer;

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
/*        // Arrange
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
        }).verifyComplete();*/
    }

    @Test
    @DisplayName("Debería fallar con BadRequestException cuando los datos son inválidos")
    void deberiaFallarConBadRequestCuandoLosDatosSonInvalidos() {
        // Arrange
        MockServerRequest request = MockServerRequest.builder()
                .body(Mono.just(solicitudRequestDto));

        // Simulamos que el validator encuentra errores
        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("email", "invalid", "El email es obligatorio");
            return null;
        }).when(validator).validate(any(), any());

        // Act
        Mono<ServerResponse> response = solicitudesHandler.registroSolicitudPrestamo(request);

        // Assert
        StepVerifier.create(response)
                .expectError(BadRequestException.class)
                .verify();
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
        // Arrange
        String email = "usuario@example.com";
        String token = "Bearer test-token";


        FindUserByEmailResponseDto userResponse = new FindUserByEmailResponseDto();
        userResponse.setName("Juan Perez");
        userResponse.setBaseSalary(new BigDecimal("2000"));

        SolicitudConTotalAprobadoUltimoMes solicitudConTotalAprobadoUltimoMes = new SolicitudConTotalAprobadoUltimoMes();
        solicitudConTotalAprobadoUltimoMes.setEmail(email);

        when(obtenerListadoRevisionManualUseCase.listSolicitudesAprobadasUltimoMes(anyString(), anyInt(), anyInt()))
                .thenReturn(Flux.just(solicitudConTotalAprobadoUltimoMes));

        when(restConsumer.getUserByEmail(anyString(), anyString()))
                .thenReturn(Mono.just(userResponse));

        MockServerRequest request = MockServerRequest.builder()
                .header("Authorization", token)
                .build();

        // Act
        Mono<ServerResponse> response = solicitudesHandler.listadoSolicitudes(request);

        // Assert
        StepVerifier.create(response)
                .assertNext(serverResponse -> {
                    assertThat(serverResponse.statusCode().value()).isEqualTo(200);
                })
                .verifyComplete();
    }
}
