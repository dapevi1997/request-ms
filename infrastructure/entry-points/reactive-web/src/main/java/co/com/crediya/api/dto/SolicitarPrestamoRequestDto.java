package co.com.crediya.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "DTO para solicitar un préstamo")
public class SolicitarPrestamoRequestDto implements Serializable {

    @JsonProperty("monto")
    @NotNull(message = "El campo monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Schema(description = "Monto del préstamo solicitado", examples = "50000.00")
    private BigDecimal monto;

    @JsonProperty("plazo")
    @NotNull(message = "El campo plazo no puede ser nulo")
    @Min(value = 1, message = "El plazo debe ser mayor a cero")
    @Schema(description = "Plazo en meses para el préstamo", examples = "12")
    private Integer plazo;

    @JsonProperty("email")
    @NotNull(message = "El campo email no puede ser nulo")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El email debe tener un formato válido")
    @Schema(description = "Email del solicitante", examples = "usuario@ejemplo.com")
    private String email;

    @JsonProperty("id_tipo_prestamo")
    @NotNull(message = "El campo id_tipo_prestamo no puede ser nulo")
    @Min(value = 1, message = "El ID del tipo de préstamo debe ser mayor a cero")
    @Schema(description = "ID del tipo de préstamo", examples = "1")
    private Long idTipoPrestamo;

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdTipoPrestamo() {
        return idTipoPrestamo;
    }

    public void setIdTipoPrestamo(Long idTipoPrestamo) {
        this.idTipoPrestamo = idTipoPrestamo;
    }

    @Override
    public String toString() {
        return "SolicitarPrestamoRequestDto{" + "monto=" + monto + ", plazo=" + plazo + ", email='"
                + email + '\'' + ", idTipoPrestamo=" + idTipoPrestamo + '}';
    }
}
