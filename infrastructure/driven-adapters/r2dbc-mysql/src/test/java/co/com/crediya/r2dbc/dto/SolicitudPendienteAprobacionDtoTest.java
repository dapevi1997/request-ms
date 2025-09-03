package co.com.crediya.r2dbc.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class SolicitudPendienteAprobacionDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Arrange
        String email = "test@mail.com";
        BigDecimal monto = BigDecimal.valueOf(1000);
        Integer plazo = 12;
        String tipoPrestamo = "Personal";
        String estado = "Pendiente";
        BigDecimal totalUltimos30Dias = BigDecimal.valueOf(5000);
        BigDecimal tasaInteres = BigDecimal.valueOf(5.5);

        // Act
        SolicitudPendienteAprobacionDto dto = new SolicitudPendienteAprobacionDto(
                email, monto, plazo, tipoPrestamo, estado, totalUltimos30Dias, tasaInteres
        );

        // Assert
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getMonto()).isEqualByComparingTo(monto);
        assertThat(dto.getPlazo()).isEqualTo(plazo);
        assertThat(dto.getTipoPrestamo()).isEqualTo(tipoPrestamo);
        assertThat(dto.getEstado()).isEqualTo(estado);
        assertThat(dto.getTotalMontoAprobadoUltimoMes()).isEqualByComparingTo(totalUltimos30Dias);
        assertThat(dto.getTasaInteres()).isEqualByComparingTo(tasaInteres);
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        // Arrange
        SolicitudPendienteAprobacionDto dto = new SolicitudPendienteAprobacionDto();

        // Act
        dto.setEmail("nuevo@mail.com");
        dto.setMonto(BigDecimal.valueOf(2000));
        dto.setPlazo(24);
        dto.setTipoPrestamo("Hipotecario");
        dto.setEstado("Aprobado");
        dto.setTotalMontoAprobadoUltimoMes(BigDecimal.valueOf(10000));
        dto.setTasaInteres(BigDecimal.valueOf(3.5));

        // Assert
        assertThat(dto.getEmail()).isEqualTo("nuevo@mail.com");
        assertThat(dto.getMonto()).isEqualByComparingTo("2000");
        assertThat(dto.getPlazo()).isEqualTo(24);
        assertThat(dto.getTipoPrestamo()).isEqualTo("Hipotecario");
        assertThat(dto.getEstado()).isEqualTo("Aprobado");
        assertThat(dto.getTotalMontoAprobadoUltimoMes()).isEqualByComparingTo("10000");
        assertThat(dto.getTasaInteres()).isEqualByComparingTo("3.5");
    }

    @Test
    void testBuilder() {
        // Arrange & Act
        SolicitudPendienteAprobacionDto dto = SolicitudPendienteAprobacionDto.builder()
                .email("builder@mail.com")
                .monto(BigDecimal.valueOf(1500))
                .plazo(6)
                .tipoPrestamo("Consumo")
                .estado("Rechazado")
                .totalMontoAprobadoUltimoMes(BigDecimal.valueOf(2000))
                .tasaInteres(BigDecimal.valueOf(7.2))
                .build();

        // Assert
        assertThat(dto.getEmail()).isEqualTo("builder@mail.com");
        assertThat(dto.getMonto()).isEqualByComparingTo("1500");
        assertThat(dto.getPlazo()).isEqualTo(6);
        assertThat(dto.getTipoPrestamo()).isEqualTo("Consumo");
        assertThat(dto.getEstado()).isEqualTo("Rechazado");
        assertThat(dto.getTotalMontoAprobadoUltimoMes()).isEqualByComparingTo("2000");
        assertThat(dto.getTasaInteres()).isEqualByComparingTo("7.2");
    }

    @Test
    void testToBuilder() {
        // Arrange
        SolicitudPendienteAprobacionDto dto = SolicitudPendienteAprobacionDto.builder()
                .email("original@mail.com")
                .monto(BigDecimal.valueOf(3000))
                .plazo(18)
                .tipoPrestamo("Vehículo")
                .estado("Pendiente")
                .totalMontoAprobadoUltimoMes(BigDecimal.valueOf(4000))
                .tasaInteres(BigDecimal.valueOf(4.8))
                .build();

        // Act
        SolicitudPendienteAprobacionDto modified = dto.toBuilder()
                .estado("Aprobado")
                .build();

        // Assert
        assertThat(modified.getEstado()).isEqualTo("Aprobado");
        assertThat(modified.getEmail()).isEqualTo("original@mail.com");
        assertThat(modified.getMonto()).isEqualByComparingTo("3000");
    }
}