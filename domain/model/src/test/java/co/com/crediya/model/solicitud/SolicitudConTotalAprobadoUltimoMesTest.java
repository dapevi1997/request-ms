package co.com.crediya.model.solicitud;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudConTotalAprobadoUltimoMesTest {

    @Test
    void shouldSetAndGetTotalMontoAprobadoUltimoMes() {
        // Arrange
        SolicitudConTotalAprobadoUltimoMes solicitud = new SolicitudConTotalAprobadoUltimoMes();
        BigDecimal expectedMonto = BigDecimal.valueOf(5000);

        // Act
        solicitud.setTotalMontoAprobadoUltimoMes(expectedMonto);

        // Assert
        assertEquals(expectedMonto, solicitud.getTotalMontoAprobadoUltimoMes());
    }

    @Test
    void shouldSetAndGetTipoPrestamo() {
        // Arrange
        SolicitudConTotalAprobadoUltimoMes solicitud = new SolicitudConTotalAprobadoUltimoMes();
        String expectedTipo = "Hipotecario";

        // Act
        solicitud.setTipoPrestamo(expectedTipo);

        // Assert
        assertEquals(expectedTipo, solicitud.getTipoPrestamo());
    }

    @Test
    void shouldSetAndGetEstado() {
        // Arrange
        SolicitudConTotalAprobadoUltimoMes solicitud = new SolicitudConTotalAprobadoUltimoMes();
        String expectedEstado = "APROBADO";

        // Act
        solicitud.setEstado(expectedEstado);

        // Assert
        assertEquals(expectedEstado, solicitud.getEstado());
    }

    @Test
    void shouldSetAndGetTasaInteres() {
        // Arrange
        SolicitudConTotalAprobadoUltimoMes solicitud = new SolicitudConTotalAprobadoUltimoMes();
        BigDecimal expectedTasa = BigDecimal.valueOf(12.5);

        // Act
        solicitud.setTasaInteres(expectedTasa);

        // Assert
        assertEquals(expectedTasa, solicitud.getTasaInteres());
    }

    @Test
    void shouldAllowNullValues() {
        // Arrange
        SolicitudConTotalAprobadoUltimoMes solicitud = new SolicitudConTotalAprobadoUltimoMes();

        // Act
        solicitud.setTotalMontoAprobadoUltimoMes(null);
        solicitud.setTipoPrestamo(null);
        solicitud.setEstado(null);
        solicitud.setTasaInteres(null);

        // Assert
        assertNull(solicitud.getTotalMontoAprobadoUltimoMes());
        assertNull(solicitud.getTipoPrestamo());
        assertNull(solicitud.getEstado());
        assertNull(solicitud.getTasaInteres());
    }
}
