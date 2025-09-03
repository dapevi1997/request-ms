package co.com.crediya.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Schema(description = "DTO para solicitar un préstamo")
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class SolicitudResponseDto implements Serializable {

    @Schema(description = "Email del solicitante", examples = "usuario@ejemplo.com")
    private String email;

    @Schema(description = "ID del tipo de préstamo", examples = "1")
    private Long idPrestamo;

    @Schema(description = "Mensaje del servidor", examples = "Solicitud creada con éxito")
    private String mensaje;

    @Schema(description = "Estampa de tiempo de la solicitud", examples = "1256564414")
    private final String timestamp = String.valueOf(System.currentTimeMillis());
}
