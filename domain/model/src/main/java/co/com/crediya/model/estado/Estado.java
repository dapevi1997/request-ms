package co.com.crediya.model.estado;

import co.com.crediya.model.utils.ValidacionesDominio;

public class Estado {
    private Long idEstado;
    private String nombre;
    private String descripcion;

    public Estado() {
    }

    public Estado(Long idEstado, String nombre, String descripcion) {
        ValidacionesDominio.validarPositivo(idEstado, "El ID del estado");
        ValidacionesDominio.validarNoNullOVacio(nombre, "El nombre del estado");
        ValidacionesDominio.validarNoNullOVacio(descripcion, "La descripción del estado");

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
}
