package co.com.crediya.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO para solicitar un préstamo")
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class SolicitudListaPendientesRevisionResponseDto implements Serializable {
    private String email;
    private BigDecimal monto;
    private Integer plazo;
    private String tipoPrestamo;
    private String estado;
    private BigDecimal totalMontoAprobadoUltimoMes;
    private BigDecimal tasaInteres;
    private String nombreUsuario;
    private BigDecimal salarioBase;
    private LocalDate fechaCreacion;
}
