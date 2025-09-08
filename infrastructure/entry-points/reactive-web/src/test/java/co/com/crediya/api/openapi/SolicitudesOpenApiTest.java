package co.com.crediya.api.openapi;

import org.junit.jupiter.api.Test;
import org.springdoc.core.fn.builders.operation.Builder;

import static org.assertj.core.api.Assertions.assertThat;

class SolicitudesOpenApiTest {

    @Test
    void testRegistroSolicitudPrestamoBuilderNotNull() {
        Builder builder = Builder.operationBuilder();

        Builder result = SolicitudesOpenApi.registroSolicitudPrestamo(builder);

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(builder); // fluidez
    }

    @Test
    void testRegistroSolicitudPrestamoDoesNotThrow() {
        Builder builder = Builder.operationBuilder();

        SolicitudesOpenApi.registroSolicitudPrestamo(builder);
    }

    @Test
    void testListadoSolicitudesBuilderNotNull() {
        Builder builder = Builder.operationBuilder();

        Builder result = SolicitudesOpenApi.listadoSolicitudes(builder);

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(builder);
    }

    @Test
    void testListadoSolicitudesDoesNotThrow() {
        Builder builder = Builder.operationBuilder();

        SolicitudesOpenApi.listadoSolicitudes(builder);
    }
}
