package co.com.crediya.api.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BadRequestExceptionTest {

    @Test
    @DisplayName("Debería crear BadRequestException con mensaje")
    void deberiaCrearBadRequestExceptionConMensaje() {
        // Arrange
        String mensaje = "Datos de solicitud inválidos";

        // Act
        BadRequestException exception = new BadRequestException(mensaje);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(mensaje);
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Debería crear BadRequestException con mensaje nulo")
    void deberiaCrearBadRequestExceptionConMensajeNulo() {
        // Arrange
        String mensaje = null;

        // Act
        BadRequestException exception = new BadRequestException(mensaje);

        // Assert
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    @DisplayName("Debería crear BadRequestException con mensaje vacío")
    void deberiaCrearBadRequestExceptionConMensajeVacio() {
        // Arrange
        String mensaje = "";

        // Act
        BadRequestException exception = new BadRequestException(mensaje);

        // Assert
        assertThat(exception.getMessage()).isEmpty();
    }

    @Test
    @DisplayName("Debería poder ser lanzada como excepción")
    void deberiaPoderSerLanzadaComoExcepcion() {
        // Arrange
        String mensaje = "Error de validación";

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException(mensaje);
        });

        assertThat(exception.getMessage()).isEqualTo(mensaje);
    }

    @Test
    @DisplayName("Debería heredar de RuntimeException")
    void deberiaHeredarDeRuntimeException() {
        // Arrange
        String mensaje = "Test exception";

        // Act
        BadRequestException exception = new BadRequestException(mensaje);

        // Assert
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception).isInstanceOf(Exception.class);
    }
}
