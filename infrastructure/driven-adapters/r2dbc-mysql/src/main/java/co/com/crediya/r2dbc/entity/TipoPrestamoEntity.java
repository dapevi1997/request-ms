package co.com.crediya.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("tipo_prestamo")
public class TipoPrestamoEntity {

    @Id
    @Column("id_tipo_prestamo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoPrestamo;

    @Column("nombre")
    private String nombre;

    @Column("monto_maximo")
    private BigDecimal montoMaximo;

    @Column("monto_minimo")
    private BigDecimal montoMinimo;

    @Column("tasa_interes")
    private BigDecimal tasaInteres;

    @Column("validacion_automatica")
    private Boolean validacionAutomatica;

    public TipoPrestamoEntity() {}

    public TipoPrestamoEntity(Long idTipoPrestamo, String nombre, BigDecimal montoMaximo,
            BigDecimal montoMinimo, BigDecimal tasaInteres, Boolean validacionAutomatica) {
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
        return "TipoPrestamoEntity{" + "idTipoPrestamo=" + idTipoPrestamo + ", nombre='" + nombre
                + '\'' + ", montoMaximo=" + montoMaximo + ", montoMinimo=" + montoMinimo
                + ", tasaInteres=" + tasaInteres + ", validacionAutomatica=" + validacionAutomatica
                + '}';
    }
}
