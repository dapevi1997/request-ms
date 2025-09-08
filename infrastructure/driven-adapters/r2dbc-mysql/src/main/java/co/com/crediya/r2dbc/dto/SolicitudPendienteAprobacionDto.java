package co.com.crediya.r2dbc.dto;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class SolicitudPendienteAprobacionDto implements Serializable {
    private String email;
    private BigDecimal monto;
    private Integer plazo;
    @Column("nombre_tipo_prestamo")
    private String tipoPrestamo;
    @Column("nombre_estado")
    private String estado;
    @Column("total_ultimos_30_dias")
    private BigDecimal totalMontoAprobadoUltimoMes;
    @Column("tasa_interes")
    private BigDecimal tasaInteres;
}
