package co.com.crediya.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class SolicitudListaPendientesRevisionResponseDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        SolicitudListaPendientesRevisionResponseDto dto =
                new SolicitudListaPendientesRevisionResponseDto(
                        "test@example.com",
                        BigDecimal.valueOf(5000),
                        12,
                        "PERSONAL",
                        "PENDIENTE",
                        BigDecimal.valueOf(10000),
                        BigDecimal.valueOf(0.12),
                        "Juan Pérez",
                        BigDecimal.valueOf(2000)
                );

        assertThat(dto.getEmail()).isEqualTo("test@example.com");
        assertThat(dto.getMonto()).isEqualByComparingTo("5000");
        assertThat(dto.getPlazo()).isEqualTo(12);
        assertThat(dto.getTipoPrestamo()).isEqualTo("PERSONAL");
        assertThat(dto.getEstado()).isEqualTo("PENDIENTE");
        assertThat(dto.getTotalMontoAprobadoUltimoMes()).isEqualByComparingTo("10000");
        assertThat(dto.getTasaInteres()).isEqualByComparingTo("0.12");
        assertThat(dto.getNombreUsuario()).isEqualTo("Juan Pérez");
        assertThat(dto.getSalarioBase()).isEqualByComparingTo("2000");
    }

    @Test
    void testBuilder() {
        SolicitudListaPendientesRevisionResponseDto dto = SolicitudListaPendientesRevisionResponseDto.builder()
                .email("builder@example.com")
                .monto(BigDecimal.valueOf(15000))
                .plazo(24)
                .tipoPrestamo("HIPOTECARIO")
                .estado("APROBADA")
                .totalMontoAprobadoUltimoMes(BigDecimal.valueOf(30000))
                .tasaInteres(BigDecimal.valueOf(0.09))
                .nombreUsuario("Ana Gómez")
                .salarioBase(BigDecimal.valueOf(5000))
                .build();

        assertThat(dto.getEmail()).isEqualTo("builder@example.com");
        assertThat(dto.getMonto()).isEqualByComparingTo("15000");
        assertThat(dto.getPlazo()).isEqualTo(24);
        assertThat(dto.getTipoPrestamo()).isEqualTo("HIPOTECARIO");
        assertThat(dto.getEstado()).isEqualTo("APROBADA");
        assertThat(dto.getTotalMontoAprobadoUltimoMes()).isEqualByComparingTo("30000");
        assertThat(dto.getTasaInteres()).isEqualByComparingTo("0.09");
        assertThat(dto.getNombreUsuario()).isEqualTo("Ana Gómez");
        assertThat(dto.getSalarioBase()).isEqualByComparingTo("5000");
    }

    @Test
    void testToBuilder() {
        SolicitudListaPendientesRevisionResponseDto original = SolicitudListaPendientesRevisionResponseDto.builder()
                .email("original@example.com")
                .monto(BigDecimal.valueOf(8000))
                .plazo(6)
                .tipoPrestamo("VEHICULAR")
                .estado("RECHAZADA")
                .totalMontoAprobadoUltimoMes(BigDecimal.valueOf(0))
                .tasaInteres(BigDecimal.valueOf(0.15))
                .nombreUsuario("Luis Ramírez")
                .salarioBase(BigDecimal.valueOf(2500))
                .build();

        SolicitudListaPendientesRevisionResponseDto modified = original.toBuilder()
                .estado("APROBADA")
                .build();

        assertThat(modified.getEstado()).isEqualTo("APROBADA");
        assertThat(modified.getEmail()).isEqualTo("original@example.com"); // el resto se conserva
    }
}
