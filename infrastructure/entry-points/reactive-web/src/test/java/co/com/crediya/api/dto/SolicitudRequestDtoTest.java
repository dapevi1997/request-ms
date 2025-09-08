package co.com.crediya.api.dto;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SolicitudRequestDtoTest {

    private SolicitudRequestDto solicitudRequestDto;

    @BeforeEach
    void setUp() {
        // Arrange
        solicitudRequestDto = new SolicitudRequestDto();
    }

    @Test
    @DisplayName("Debería crear SolicitudRequestDto con valores por defecto")
    void deberiaCrearSolicitudRequestDtoConValoresPorDefecto() {
        // Act & Assert
        assertThat(solicitudRequestDto.getMonto()).isNull();
        assertThat(solicitudRequestDto.getPlazo()).isNull();
        assertThat(solicitudRequestDto.getEmail()).isNull();
        assertThat(solicitudRequestDto.getIdTipoPrestamo()).isNull();
    }

    @Test
    @DisplayName("Debería establecer y obtener monto correctamente")
    void deberiaEstablecerYObtenerMontoCorrectamente() {
        // Arrange
        BigDecimal monto = new BigDecimal("50000");

        // Act
        solicitudRequestDto.setMonto(monto);

        // Assert
        assertThat(solicitudRequestDto.getMonto()).isEqualTo(monto);
    }

    @Test
    @DisplayName("Debería establecer y obtener plazo correctamente")
    void deberiaEstablecerYObtenerPlazoCorrectamente() {
        // Arrange
        Integer plazo = 12;

        // Act
        solicitudRequestDto.setPlazo(plazo);

        // Assert
        assertThat(solicitudRequestDto.getPlazo()).isEqualTo(plazo);
    }

    @Test
    @DisplayName("Debería establecer y obtener email correctamente")
    void deberiaEstablecerYObtenerEmailCorrectamente() {
        // Arrange
        String email = "usuario@example.com";

        // Act
        solicitudRequestDto.setEmail(email);

        // Assert
        assertThat(solicitudRequestDto.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Debería establecer y obtener ID tipo prestamo correctamente")
    void deberiaEstablecerYObtenerIdTipoPrestamoCorrectamente() {
        // Arrange
        Long idTipoPrestamo = 1L;

        // Act
        solicitudRequestDto.setIdTipoPrestamo(idTipoPrestamo);

        // Assert
        assertThat(solicitudRequestDto.getIdTipoPrestamo()).isEqualTo(idTipoPrestamo);
    }

    @Test
    @DisplayName("Debería crear objeto completo con todos los campos")
    void deberiaCrearObjetoCompletoConTodosLosCampos() {
        // Arrange
        BigDecimal monto = new BigDecimal("100000");
        Integer plazo = 24;
        String email = "test@example.com";
        Long idTipoPrestamo = 2L;

        // Act
        solicitudRequestDto.setMonto(monto);
        solicitudRequestDto.setPlazo(plazo);
        solicitudRequestDto.setEmail(email);
        solicitudRequestDto.setIdTipoPrestamo(idTipoPrestamo);

        // Assert
        assertThat(solicitudRequestDto.getMonto()).isEqualTo(monto);
        assertThat(solicitudRequestDto.getPlazo()).isEqualTo(plazo);
        assertThat(solicitudRequestDto.getEmail()).isEqualTo(email);
        assertThat(solicitudRequestDto.getIdTipoPrestamo()).isEqualTo(idTipoPrestamo);
    }
}
