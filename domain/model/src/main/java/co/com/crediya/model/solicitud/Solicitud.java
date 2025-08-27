package co.com.crediya.model.solicitud;

import java.math.BigDecimal;
import co.com.crediya.model.utils.ValidacionesDominio;

public class Solicitud {
    private Long idSolicitud;
    private BigDecimal monto;
    private Integer plazo;
    private String email;
    private String documentoIdentidad;
    private Long idEstado;
    private Long idTipoPrestamo;

    public Solicitud() {}

    public Solicitud(BigDecimal monto, Integer plazo, String email, String documentoIdentidad, Long idEstado,
                     Long idTipoPrestamo) {
        ValidacionesDominio.validarNoNullOVacio(documentoIdentidad, "El documento de identidad"); ;;
        ValidacionesDominio.validarNoNegativo(monto, "El monto");
        ValidacionesDominio.validarPositivo(plazo, "El plazo");
        ValidacionesDominio.validarEmail(email, "El email");
        ValidacionesDominio.validarPositivo(idEstado, "El ID del estado");
        ValidacionesDominio.validarPositivo(idTipoPrestamo, "El ID del tipo de préstamo");

        this.monto = monto;
        this.plazo = plazo;
        this.email = email;
        this.idEstado = idEstado;
        this.idTipoPrestamo = idTipoPrestamo;
    }

    public Solicitud(Long idSolicitud, BigDecimal monto, Integer plazo, String email, String documentoIdentidad, Long idEstado,
                     Long idTipoPrestamo) {
        this.documentoIdentidad = documentoIdentidad;
        ValidacionesDominio.validarPositivo(idSolicitud, "El ID de la solicitud");
        ValidacionesDominio.validarNoNegativo(monto, "El monto");
        ValidacionesDominio.validarPositivo(plazo, "El plazo");
        ValidacionesDominio.validarEmail(email, "El email");
        ValidacionesDominio.validarPositivo(idEstado, "El ID del estado");
        ValidacionesDominio.validarPositivo(idTipoPrestamo, "El ID del tipo de préstamo");

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

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "idSolicitud=" + idSolicitud +
                ", monto=" + monto +
                ", plazo=" + plazo +
                ", email='" + email + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", idEstado=" + idEstado +
                ", idTipoPrestamo=" + idTipoPrestamo +
                '}';
    }
}
