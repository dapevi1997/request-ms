package co.com.crediya.api.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import co.com.crediya.api.handler.SolicitudesHandler;
import co.com.crediya.api.router.SolicitudesRouterRest;
import co.com.crediya.api.security.ValidacionUsuarioCrearSolicitudPrestamoFilter;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;

@ContextConfiguration(classes = {SolicitudesRouterRest.class, SolicitudesHandler.class,
        ConfigTest.TestConfig.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class,
        ValidacionUsuarioCrearSolicitudPrestamoFilter.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

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

        @Bean
        @Primary
        public LoggerGateway loggerGateway() {
            return Mockito.mock(LoggerGateway.class);
        }
    }

    @Test
    void corsConfigurationShouldAllowOrigins() {
        webTestClient.get().uri("/api/v1/solicitud").exchange().expectStatus().isUnauthorized() // Esperamos
                                                                                                // 401
                                                                                                // porque
                                                                                                // no
                                                                                                // hay
                                                                                                // autenticación
                .expectHeader().exists("Cache-Control").expectHeader().exists("Pragma")
                .expectHeader().exists("X-Content-Type-Options").expectHeader()
                .exists("X-Frame-Options").expectHeader().exists("Referrer-Policy").expectHeader()
                .valueEquals("X-Content-Type-Options", "nosniff").expectHeader()
                .valueEquals("X-Frame-Options", "DENY").expectHeader()
                .valueEquals("Referrer-Policy", "no-referrer");
    }

}
