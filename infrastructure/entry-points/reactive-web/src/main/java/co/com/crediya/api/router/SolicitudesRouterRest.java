package co.com.crediya.api.router;

import co.com.crediya.api.handler.SolicitudesHandler;
import co.com.crediya.api.openapi.SolicitudesOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
public class SolicitudesRouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(SolicitudesHandler solicitudesHandler) {
        return route().POST("/api/v1/solicitud", accept(MediaType.APPLICATION_JSON), solicitudesHandler::registroSolicitudPrestamo, SolicitudesOpenApi::registroSolicitudPrestamo).build();
    }
}
