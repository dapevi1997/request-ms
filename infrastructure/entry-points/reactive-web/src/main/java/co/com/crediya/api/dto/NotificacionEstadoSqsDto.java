package co.com.crediya.api.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NotificacionEstadoSqsDto {
    private String mensaje;
    private String correo;
    private String estado;
}
