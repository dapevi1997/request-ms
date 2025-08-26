package co.com.crediya.model.tipoprestamo;

import java.math.BigDecimal;
import co.com.crediya.model.utils.ValidationUtils;

public class TipoPrestamo {
    private Long idTipoPrestamo;
    private String nombre;
    private BigDecimal montoMaximo;
    private BigDecimal montoMinimo;
    private BigDecimal tasaInteres;
    private Boolean validacionAutomatica;

    public TipoPrestamo() {}

    public TipoPrestamo(Long idTipoPrestamo, String nombre, BigDecimal montoMaximo,
            BigDecimal montoMinimo, BigDecimal tasaInteres, Boolean validacionAutomatica) {
        ValidationUtils.validatePositiveLong(idTipoPrestamo, "El ID del tipo de préstamo");
        ValidationUtils.validateNotNullString(nombre, "El nombre del tipo de préstamo");
        ValidationUtils.validatePositiveBigDecimal(montoMaximo, "El monto máximo");
        ValidationUtils.validatePositiveBigDecimal(montoMinimo, "El monto mínimo");
        ValidationUtils.validateNonNegativeBigDecimal(tasaInteres, "La tasa de interés");
        ValidationUtils.validateNotNullBoolean(validacionAutomatica,
                "El indicador de validación automática");

        this.idTipoPrestamo = idTipoPrestamo;
        this.nombre = nombre;
        this.montoMaximo = montoMaximo;
        this.montoMinimo = montoMinimo;
        this.tasaInteres = tasaInteres;
        this.validacionAutomatica = validacionAutomatica;
    }

    public Long getIdTipoPrestamo() {
        return idTipoPrestamo;
    }

    public void setIdTipoPrestamo(Long idTipoPrestamo) {
        this.idTipoPrestamo = idTipoPrestamo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(BigDecimal montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public BigDecimal getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(BigDecimal montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public Boolean getValidacionAutomatica() {
        return validacionAutomatica;
    }

    public void setValidacionAutomatica(Boolean validacionAutomatica) {
        this.validacionAutomatica = validacionAutomatica;
    }

    @Override
    public String toString() {
        return "TipoPrestamo{" + "idTipoPrestamo=" + idTipoPrestamo + ", nombre='" + nombre + '\''
                + ", montoMaximo=" + montoMaximo + ", montoMinimo=" + montoMinimo + ", tasaInteres="
                + tasaInteres + ", validacionAutomatica=" + validacionAutomatica + '}';
    }
}
