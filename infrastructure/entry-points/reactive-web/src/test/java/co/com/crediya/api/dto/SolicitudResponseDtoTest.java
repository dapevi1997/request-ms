package co.com.crediya.api.dto;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SolicitudResponseDtoTest {

    private SolicitudResponseDto solicitudResponseDto;

    @BeforeEach
    void setUp() {
        // Arrange
        solicitudResponseDto = new SolicitudResponseDto();
    }

    @Test
    @DisplayName("Debería crear SolicitudResponseDto con valores por defecto")
    void deberiaCrearSolicitudResponseDtoConValoresPorDefecto() {
        // Act & Assert
        assertThat(solicitudResponseDto.getIdPrestamo()).isNull();
        assertThat(solicitudResponseDto.getEmail()).isNull();
        assertThat(solicitudResponseDto.getMensaje()).isNull();
        assertThat(solicitudResponseDto.getTimestamp()).isNotNull();

    }

    @Test
    @DisplayName("Debería establecer y obtener ID prestamo correctamente")
    void deberiaEstablecerYObtenerIdPrestamoCorrectamente() {
        // Arrange
        Long idPrestamo = 1L;

        // Act
        solicitudResponseDto.setIdPrestamo(idPrestamo);

        // Assert
        assertThat(solicitudResponseDto.getIdPrestamo()).isEqualTo(idPrestamo);
    }

    @Test
    @DisplayName("Debería establecer y obtener email correctamente")
    void deberiaEstablecerYObtenerEmailCorrectamente() {
        // Arrange
        String email = "usuario@example.com";

        // Act
        solicitudResponseDto.setEmail(email);

        // Assert
        assertThat(solicitudResponseDto.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Debería establecer y obtener mensaje correctamente")
    void deberiaEstablecerYObtenerMensajeCorrectamente() {
        // Arrange
        String mensaje = "Solicitud creada correctamente";

        // Act
        solicitudResponseDto.setMensaje(mensaje);

        // Assert
        assertThat(solicitudResponseDto.getMensaje()).isEqualTo(mensaje);
    }

    @Test
    @DisplayName("Debería generar timestamp automáticamente")
    void deberiaGenerarTimestampAutomaticamente() {
        // Act & Assert
        assertThat(solicitudResponseDto.getTimestamp()).isNotNull();
        assertThat(solicitudResponseDto.getTimestamp()).isNotEmpty();
        // Verificar que es un número (timestamp)
        assertThat(Long.parseLong(solicitudResponseDto.getTimestamp())).isPositive();
    }

    @Test
    @DisplayName("Debería crear objeto completo con todos los campos")
    void deberiaCrearObjetoCompletoConTodosLosCampos() {
        // Arrange
        Long idPrestamo = 123L;
        String email = "test@example.com";
        String mensaje = "Solicitud procesada exitosamente";

        // Act
        solicitudResponseDto.setIdPrestamo(idPrestamo);
        solicitudResponseDto.setEmail(email);
        solicitudResponseDto.setMensaje(mensaje);

        // Assert
        assertThat(solicitudResponseDto.getIdPrestamo()).isEqualTo(idPrestamo);
        assertThat(solicitudResponseDto.getEmail()).isEqualTo(email);
        assertThat(solicitudResponseDto.getMensaje()).isEqualTo(mensaje);
        assertThat(solicitudResponseDto.getTimestamp()).isNotNull();
    }
}
