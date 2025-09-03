package co.com.crediya.model.solicitud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.exceptions.InvalidEntityException;

class SolicitudTest {
    @Test
    void deberiaCrearSolicitudCorrectamenteCuandoTodosLosParametrosSonValidos() {
        // Arrange
        BigDecimal monto = new BigDecimal("50000");
        Integer plazo = 12;
        String email = "usuario@example.com";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act
        Solicitud solicitud =
                new Solicitud(monto, plazo, email, idEstado, idTipoPrestamo);

        // Assert
        assertNotNull(solicitud);
        assertEquals(monto, solicitud.getMonto());
        assertEquals(plazo, solicitud.getPlazo());
        assertEquals(email, solicitud.getEmail());
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
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act
        Solicitud solicitud = new Solicitud(idSolicitud, monto, plazo, email,
                idEstado, idTipoPrestamo);

        // Assert
        assertNotNull(solicitud);
        assertEquals(idSolicitud, solicitud.getIdSolicitud());
        assertEquals(monto, solicitud.getMonto());
        assertEquals(plazo, solicitud.getPlazo());
        assertEquals(email, solicitud.getEmail());
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
        assertNull(solicitud.getIdEstado());
        assertNull(solicitud.getIdTipoPrestamo());
    }

    @Test
    void deberiaLanzarExcepcionCuandoMontoEsNegativo() {
        // Arrange
        BigDecimal monto = new BigDecimal("-1000");
        Integer plazo = 12;
        String email = "usuario@example.com";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Solicitud(monto, plazo, email, idEstado, idTipoPrestamo);
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
            new Solicitud(monto, plazo, email, idEstado, idTipoPrestamo);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoEmailEsInvalido() {
        // Arrange
        BigDecimal monto = new BigDecimal("50000");
        Integer plazo = 12;
        String email = "email_invalido";
        Long idEstado = 1L;
        Long idTipoPrestamo = 1L;

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Solicitud(monto, plazo, email, idEstado, idTipoPrestamo);
        });
    }

    @Test
    void gettersYSettersFuncionanCorrectamente() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        Long idSolicitud = 10L;
        BigDecimal monto = new BigDecimal("12345");
        Integer plazo = 24;
        String email = "test@correo.com";
        String documentoIdentidad = "99999999";
        Long idEstado = 2L;
        Long idTipoPrestamo = 3L;

        // Act
        solicitud.setIdSolicitud(idSolicitud);
        solicitud.setMonto(monto);
        solicitud.setPlazo(plazo);
        solicitud.setEmail(email);
        solicitud.setIdEstado(idEstado);
        solicitud.setIdTipoPrestamo(idTipoPrestamo);

        // Assert
        assertEquals(idSolicitud, solicitud.getIdSolicitud());
        assertEquals(monto, solicitud.getMonto());
        assertEquals(plazo, solicitud.getPlazo());
        assertEquals(email, solicitud.getEmail());
        assertEquals(idEstado, solicitud.getIdEstado());
        assertEquals(idTipoPrestamo, solicitud.getIdTipoPrestamo());
    }
}
