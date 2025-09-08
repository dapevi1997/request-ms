package co.com.crediya.model.solicitud;

import java.math.BigDecimal;
import java.time.LocalDate;

import co.com.crediya.model.utils.ValidacionesDominio;

public class Solicitud {
    private Long idSolicitud;
    private BigDecimal monto;
    private Integer plazo;
    private String email;
    private Long idEstado;
    private Long idTipoPrestamo;
    private LocalDate fechaCreacion;

    public Solicitud() {}

    public Solicitud(BigDecimal monto, Integer plazo, String email, Long idEstado,
                     Long idTipoPrestamo) {
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

    public Solicitud(Long idSolicitud, BigDecimal monto, Integer plazo, String email, Long idEstado,
                     Long idTipoPrestamo) {
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
