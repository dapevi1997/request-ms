package co.com.crediya.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;

import co.com.crediya.api.exceptions.GlobalExceptionHandler;
import co.com.crediya.api.handler.SolicitudesHandler;
import co.com.crediya.api.router.SolicitudesRouterRest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import co.com.crediya.api.dto.SolicitudRequestDto;
import co.com.crediya.api.dto.SolicitudResponseDto;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {SolicitudesRouterRest.class, SolicitudesHandler.class,
        SolicitudesRouterRestTest.TestConfig.class, GlobalExceptionHandler.class, OtherBeans.class})
@WebFluxTest
class SolicitudesRouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase() {
            return Mockito.mock(EnviarSolicitudPrestamoUseCase.class);
        }

        @Bean
        @Primary
        public ObjectMapper objectMapper() {
            return Mockito.mock(ObjectMapper.class);
        }
    }

    @Test
    @DisplayName("Debería crear solicitud exitosamente cuando datos son válidos")
    void deberiaCrearSolicitudExitosamenteCuandoDatosSonValidos() {
        // Arrange
        SolicitudRequestDto request = new SolicitudRequestDto();
        request.setDocumentoIdentidad("12345678");
        request.setMonto(new BigDecimal("50000"));
        request.setPlazo(12);
        request.setEmail("usuario@example.com");
        request.setIdTipoPrestamo(1L);

        Solicitud solicitud = new Solicitud(new BigDecimal("50000"), 12, "usuario@example.com",
                "12345678", 1L, 1L);

        Solicitud solicitudGuardada = new Solicitud(1L, new BigDecimal("50000"), 12,
                "usuario@example.com", "12345678", 1L, 1L);

        when(objectMapper.map(any(SolicitudRequestDto.class), any(Class.class)))
                .thenReturn(solicitud);
        when(enviarSolicitudPrestamoUseCase.guardarSolicitud(any(Solicitud.class)))
                .thenReturn(Mono.just(solicitudGuardada));

        // Act & Assert
        webTestClient.post().uri("/api/v1/solicitud").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).bodyValue(request).exchange().expectStatus()
                .isCreated().expectBody(SolicitudResponseDto.class).value(response -> {
                    Assertions.assertThat(response.getEmail()).isEqualTo("usuario@example.com");
                    Assertions.assertThat(response.getDocumentoIdentidad()).isEqualTo("12345678");
                    Assertions.assertThat(response.getIdPrestamo()).isEqualTo(1L);
                    Assertions.assertThat(response.getMensaje())
                            .isEqualTo("Solicitud creada correctamente");
                    Assertions.assertThat(response.getTimestamp()).isNotNull();
                });
    }

    @Test
    @DisplayName("Debería retornar bad request cuando datos son inválidos")
    void deberiaRetornarBadRequestCuandoDatosSonInvalidos() {
        // Arrange
        SolicitudRequestDto request = new SolicitudRequestDto();
        request.setDocumentoIdentidad(""); // Documento vacío
        request.setMonto(new BigDecimal("-1000")); // Monto negativo
        request.setPlazo(-5); // Plazo negativo
        request.setEmail("email_invalido"); // Email inválido
        request.setIdTipoPrestamo(null); // ID nulo

        // Act & Assert
        webTestClient.post().uri("/api/v1/solicitud").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).bodyValue(request).exchange().expectStatus()
                .isBadRequest();
    }
}
