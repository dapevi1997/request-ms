package co.com.crediya.model.solicitud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;

import co.com.crediya.model.exceptions.InvalidEntityException;
import org.junit.jupiter.api.Test;

class SolicitudTest {
    @Test
    void deberiaCrearSolicitudCorrectamenteCuandoTodosLosParametrosSonValidos() {
        // Arrange
        BigDecimal monto = new BigDecimal("50000");
        Integer plazo = 12;
        String email = "usuario@example.com";
        String documentoIdentidad = "12345678";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act
        Solicitud solicitud =
                new Solicitud(monto, plazo, email, documentoIdentidad, idEstado, idTipoPrestamo);

        // Assert
        assertNotNull(solicitud);
        assertEquals(monto, solicitud.getMonto());
        assertEquals(plazo, solicitud.getPlazo());
        assertEquals(email, solicitud.getEmail());
        assertEquals(documentoIdentidad, solicitud.getDocumentoIdentidad());
        assertEquals(idEstado, solicitud.getIdEstado());
        assertEquals(idTipoPrestamo, solicitud.getIdTipoPrestamo());
        assertNull(solicitud.getIdSolicitud());
    }

    @Test
    void deberiaCrearSolicitudConIdCuandoTodosLosParametrosSonValidos() {
        // Arrange
        Long idSolicitud = 1L;
        BigDecimal monto = new BigDecimal("50000");
        Integer plazo = 12;
        String email = "usuario@example.com";
        String documentoIdentidad = "12345678";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act
        Solicitud solicitud = new Solicitud(idSolicitud, monto, plazo, email, documentoIdentidad,
                idEstado, idTipoPrestamo);

        // Assert
        assertNotNull(solicitud);
        assertEquals(idSolicitud, solicitud.getIdSolicitud());
        assertEquals(monto, solicitud.getMonto());
        assertEquals(plazo, solicitud.getPlazo());
        assertEquals(email, solicitud.getEmail());
        assertEquals(documentoIdentidad, solicitud.getDocumentoIdentidad());
        assertEquals(idEstado, solicitud.getIdEstado());
        assertEquals(idTipoPrestamo, solicitud.getIdTipoPrestamo());
    }

    @Test
    void deberiaCrearSolicitudVaciaCuandoSeUsaConstructorSinParametros() {
        // Arrange & Act
        Solicitud solicitud = new Solicitud();

        // Assert
        assertNotNull(solicitud);
        assertNull(solicitud.getIdSolicitud());
        assertNull(solicitud.getMonto());
        assertNull(solicitud.getPlazo());
        assertNull(solicitud.getEmail());
        assertNull(solicitud.getDocumentoIdentidad());
        assertNull(solicitud.getIdEstado());
        assertNull(solicitud.getIdTipoPrestamo());
    }

    @Test
    void deberiaLanzarExcepcionCuandoMontoEsNegativo() {
        // Arrange
        BigDecimal monto = new BigDecimal("-1000");
        Integer plazo = 12;
        String email = "usuario@example.com";
        String documentoIdentidad = "12345678";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Solicitud(monto, plazo, email, documentoIdentidad, idEstado, idTipoPrestamo);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoPlazoEsCero() {
        // Arrange
        BigDecimal monto = new BigDecimal("50000");
        Integer plazo = 0;
        String email = "usuario@example.com";
        String documentoIdentidad = "12345678";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Solicitud(monto, plazo, email, documentoIdentidad, idEstado, idTipoPrestamo);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoEmailEsInvalido() {
        // Arrange
        BigDecimal monto = new BigDecimal("50000");
        Integer plazo = 12;
        String email = "email_invalido";
        String documentoIdentidad = "12345678";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Solicitud(monto, plazo, email, documentoIdentidad, idEstado, idTipoPrestamo);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoDocumentoIdentidadEsVacio() {
        // Arrange
        BigDecimal monto = new BigDecimal("50000");
        Integer plazo = 12;
        String email = "usuario@example.com";
        String documentoIdentidad = "";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Solicitud(monto, plazo, email, documentoIdentidad, idEstado, idTipoPrestamo);
        });
    }
}