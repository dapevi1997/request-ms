package co.com.crediya.api.dto;

import co.com.crediya.model.solicitud.Solicitud;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SolicitudDto extends Solicitud {
    private BigDecimal tasaMensual;
}
