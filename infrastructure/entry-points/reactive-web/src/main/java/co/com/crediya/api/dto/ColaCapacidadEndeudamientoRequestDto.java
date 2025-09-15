package co.com.crediya.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ColaCapacidadEndeudamientoRequestDto {
    private List<SolicitudDto> listaSolicitudesAprobadasPorUsuario;
    private SolicitudDto solicitudActual;
    private BigDecimal ingresosTotales;
}
