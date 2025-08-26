package co.com.crediya.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("estados")
public class EstadosEntity {

    @Id
    @Column("id_estado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    public EstadosEntity() {}

    public EstadosEntity(Long idEstado, String nombre, String descripcion) {
        this.idEstado = idEstado;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "EstadosEntity{" + "idEstado=" + idEstado + ", nombre='" + nombre + '\''
                + ", descripcion='" + descripcion + '\'' + '}';
    }
}
