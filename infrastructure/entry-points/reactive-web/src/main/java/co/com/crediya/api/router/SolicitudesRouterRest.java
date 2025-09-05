    package co.com.crediya.api.router;

    import co.com.crediya.api.config.SolicitudesPath;
    import co.com.crediya.api.handler.SolicitudesHandler;
    import co.com.crediya.api.openapi.SolicitudesOpenApi;
    import co.com.crediya.api.security.ValidacionUsuarioCrearSolicitudPrestamoFilter;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.reactive.function.server.RouterFunction;
    import org.springframework.web.reactive.function.server.ServerResponse;

    import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

    @Configuration
    @RequiredArgsConstructor
    public class SolicitudesRouterRest {
        private final ValidacionUsuarioCrearSolicitudPrestamoFilter filter;
        private final SolicitudesPath solicitudesPath;

        @Bean
        public RouterFunction<ServerResponse> routerSolicitudes(SolicitudesHandler solicitudesHandler) {
            return route().POST(solicitudesPath.getCrearSolicitud(), solicitudesHandler::registroSolicitudPrestamo, SolicitudesOpenApi::registroSolicitudPrestamo)
                    .build()
                    .filter(filter);
        }

        @Bean
        public RouterFunction<ServerResponse> routerRevisiones(SolicitudesHandler solicitudesHandler) {
            return route()
                    .GET(solicitudesPath.getSolicitudesRevision(), solicitudesHandler::listadoSolicitudes, SolicitudesOpenApi::listadoSolicitudes)
                    .build();
        }


    }
