package co.com.crediya.model.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantesTest {

    @Test
    void shouldHaveCorrectMensajeEstadoNoEncontrado() {
        // Arrange
        String expected = "No se encuentra estado con el id proporcionado ";

        // Act
        String actual = Constantes.MENSAJE_ESTADO_NO_ENCONTRADO;

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void shouldHaveCorrectEstadoAprobado() {
        // Arrange
        String expected = "APROBADO";

        // Act
        String actual = Constantes.ESTADO_APROBADO;

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void shouldHaveCorrectColasSqsConstants() {
        // Assert
        assertEquals("colaNotificacionEstado", Constantes.ColasSqs.COLA_NOTIFICACION_ESTADO);
        assertEquals("colaCapacidadEndeudamiento", Constantes.ColasSqs.COLA_CAPACIDAD_ENDEUDAMIENTO);
        assertEquals("colaAprobados", Constantes.ColasSqs.COLA_APROBADOS);
    }

    @Test
    void constructorShouldThrowUnsupportedOperationException() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, Constantes::new);
    }
}
