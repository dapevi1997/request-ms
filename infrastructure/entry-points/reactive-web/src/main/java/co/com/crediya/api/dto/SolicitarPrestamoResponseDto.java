package co.com.crediya.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "DTO para solicitar un préstamo")
public class SolicitarPrestamoResponseDto implements Serializable {

    @Schema(description = "Email del solicitante", examples = "usuario@ejemplo.com")
    private String email;

    @Schema(description = "ID del tipo de préstamo", examples = "1")
    private Long idPrestamo;

    @Schema(description = "Mensaje del servidor", examples = "Solicitud creada con éxito")
    private String mensaje;

    @Schema(description = "Estampa de tiempo de la solicitud", examples = "1256564414")
    private final String timestamp = String.valueOf(System.currentTimeMillis());


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SolicitarPrestamoResponseDto{" +
                "email='" + email + '\'' +
                ", idPrestamo=" + idPrestamo +
                ", mensaje='" + mensaje + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
