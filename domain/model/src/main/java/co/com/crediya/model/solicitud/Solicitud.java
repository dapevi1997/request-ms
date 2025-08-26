package co.com.crediya.model.solicitud;

import co.com.crediya.model.utils.ValidationUtils;
import java.math.BigDecimal;

public class Solicitud {
    private Long idSolicitud;
    private BigDecimal monto;
    private Integer plazo;
    private String email;
    private Long idEstado;
    private Long idTipoPrestamo;

    public Solicitud(){}

    public Solicitud(Long idSolicitud, BigDecimal monto, Integer plazo, String email, Long idEstado, Long idTipoPrestamo) {
        ValidationUtils.validatePositiveLong(idSolicitud, "El ID de la solicitud");
        ValidationUtils.validatePositiveBigDecimal(monto, "El monto");
        ValidationUtils.validatePositiveInteger(plazo, "El plazo");
        ValidationUtils.validateEmail(email, "El email");
        ValidationUtils.validatePositiveLong(idEstado, "El ID del estado");
        ValidationUtils.validatePositiveLong(idTipoPrestamo, "El ID del tipo de préstamo");
        
        this.idSolicitud = idSolicitud;
        this.monto = monto;
        this.plazo = plazo;
        this.email = email;
        this.idEstado = idEstado;
        this.idTipoPrestamo = idTipoPrestamo;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

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

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdTipoPrestamo() {
        return idTipoPrestamo;
    }

    public void setIdTipoPrestamo(Long idTipoPrestamo) {
        this.idTipoPrestamo = idTipoPrestamo;
    }

    @Override
    public String toString() {
        return "Estados{" +
                "idSolicitud=" + idSolicitud +
                ", monto='" + monto + '\'' +
                ", plazo='" + plazo + '\'' +
                ", email='" + email + '\'' +
                ", idEstado=" + idEstado +
                ", idTipoPrestamo=" + idTipoPrestamo +
                '}';
    }
}
