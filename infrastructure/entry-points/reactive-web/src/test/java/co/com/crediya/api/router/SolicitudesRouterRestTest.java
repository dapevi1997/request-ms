package co.com.crediya.api.router;

import co.com.crediya.api.config.SolicitudesPath;
import co.com.crediya.api.dto.SolicitudRequestDto;
import co.com.crediya.api.dto.SolicitudResponseDto;
import co.com.crediya.api.handler.SolicitudesHandler;
import co.com.crediya.api.security.ValidacionUsuarioCrearSolicitudPrestamoFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SolicitudesRouterRestTest {

    private WebTestClient webTestClient;

    @Mock
    private SolicitudesHandler solicitudesHandler;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ValidacionUsuarioCrearSolicitudPrestamoFilter filter;

    @BeforeEach
    void setUp() {
        // Configuración de paths de prueba
        SolicitudesPath solicitudesPath = new SolicitudesPath();
        solicitudesPath.setCrearSolicitud("/api/v1/solicitudes");
        solicitudesPath.setSolicitudesRevision("/api/v1/solicitudes/revision");


        // Crear instancia real del filtro con un ObjectMapper mockeado
        filter = new ValidacionUsuarioCrearSolicitudPrestamoFilter(objectMapper);

        // Construcción manual del router
        SolicitudesRouterRest router = new SolicitudesRouterRest(filter, solicitudesPath);
        RouterFunction<ServerResponse> routerFunction =
                router.routerSolicitudes(solicitudesHandler)
                        .and(router.routerRevisiones(solicitudesHandler));

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    @DisplayName("Crear solicitud exitosamente debe retornar 201 con datos correctos")
    void crearSolicitud_Success_ShouldReturn201() {
        SolicitudRequestDto request = new SolicitudRequestDto();
        request.setMonto(new BigDecimal("1000000.00"));
        request.setPlazo(12);
        request.setEmail("usuario@ejemplo.com");
        request.setIdTipoPrestamo(1L);

        SolicitudResponseDto expectedResponse = SolicitudResponseDto.builder()
                .email("usuario@ejemplo.com")
                .idPrestamo(1L)
                .mensaje("Solicitud creada con éxito")
                .build();

        Mono<ServerResponse> responseMono =
                ServerResponse.status(201).bodyValue(expectedResponse);

        when(solicitudesHandler.registroSolicitudPrestamo(any(ServerRequest.class)))
                .thenReturn(responseMono);

        webTestClient.post().uri("/api/v1/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(SolicitudResponseDto.class)
                .consumeWith(result -> {
                    SolicitudResponseDto body = result.getResponseBody();
                    assertNotNull(body);
                    assertEquals("usuario@ejemplo.com", body.getEmail());
                    assertEquals(1L, body.getIdPrestamo());
                    assertEquals("Solicitud creada con éxito", body.getMensaje());
                    assertNotNull(body.getTimestamp());
                });
    }

    @Test
    @DisplayName("Listar solicitudes de revisión debe retornar 200 con lista vacía")
    void listarSolicitudes_ShouldReturn200() {
        when(solicitudesHandler.listadoSolicitudes(any(ServerRequest.class)))
                .thenReturn(ServerResponse.ok().bodyValue(Collections.emptyList()));

        webTestClient.get().uri("/api/v1/solicitudes/revision")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Object.class)
                .hasSize(0);
    }

    @Test
    @DisplayName("Acceder a ruta inexistente debe retornar 404")
    void accessNonExistentRoute_ShouldReturn404() {
        webTestClient.get().uri("/api/v1/noexiste")
                .exchange()
                .expectStatus().isNotFound();
    }
}
