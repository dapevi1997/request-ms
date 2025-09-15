package co.com.crediya.sqs.listener.helper;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BodyMensajeColaActualizarDto {
    private Long idSolicitud;
    private BigDecimal monto;
    private Integer plazo;
    private String email;
    private Long idEstado;
    private Long idTipoPrestamo;
    private LocalDate fechaCreacion;
    private BigDecimal tasaMensual;
    private String estadoSolicitud;
}
