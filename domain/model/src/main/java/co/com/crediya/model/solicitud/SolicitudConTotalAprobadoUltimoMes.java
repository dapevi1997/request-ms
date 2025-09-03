package co.com.crediya.model.solicitud;

import java.math.BigDecimal;

public class SolicitudConTotalAprobadoUltimoMes extends Solicitud{
    private BigDecimal totalMontoAprobadoUltimoMes;
    private String tipoPrestamo;
    private String estado;

    public BigDecimal getTotalMontoAprobadoUltimoMes() {
        return totalMontoAprobadoUltimoMes;
    }

    public void setTotalMontoAprobadoUltimoMes(BigDecimal totalMontoAprobadoUltimoMes) {
        this.totalMontoAprobadoUltimoMes = totalMontoAprobadoUltimoMes;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
