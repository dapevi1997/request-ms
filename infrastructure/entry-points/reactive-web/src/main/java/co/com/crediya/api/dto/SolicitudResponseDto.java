package co.com.crediya.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "DTO para solicitar un préstamo")
public class SolicitudResponseDto implements Serializable {

    @Schema(description = "Email del solicitante", examples = "usuario@ejemplo.com")
    private String email;

    @Schema(description = "ID del tipo de préstamo", examples = "1")
    private Long idPrestamo;

    @Schema(description = "Mensaje del servidor", examples = "Solicitud creada con éxito")
    private String mensaje;

    @Schema(description = "Estampa de tiempo de la solicitud", examples = "1256564414")
    private final String timestamp = String.valueOf(System.currentTimeMillis());

    @Schema(description = "Documento de identidad del cliente", examples = "12345678")
    private String documentoIdentidad;


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

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    @Override
    public String toString() {
        return "SolicitarPrestamoResponseDto{" +
                "email='" + email + '\'' +
                ", idPrestamo=" + idPrestamo +
                ", mensaje='" + mensaje + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                '}';
    }
}
