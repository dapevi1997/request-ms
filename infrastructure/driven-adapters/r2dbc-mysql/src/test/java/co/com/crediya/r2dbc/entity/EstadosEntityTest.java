package co.com.crediya.r2dbc.entity;

import org.junit.jupiter.api.Test;

class EstadosEntityTest {

    @Test
    void constructorYGettersFuncionanCorrectamente() {
        Long id = 1L;
        String nombre = "APROBADO";
        String descripcion = "Aprobado por el sistema";

        EstadosEntity entity = new EstadosEntity(id, nombre, descripcion);
        org.junit.jupiter.api.Assertions.assertEquals(id, entity.getIdEstado());
        org.junit.jupiter.api.Assertions.assertEquals(nombre, entity.getNombre());
        org.junit.jupiter.api.Assertions.assertEquals(descripcion, entity.getDescripcion());
    }

    @Test
    void settersFuncionanCorrectamente() {
        EstadosEntity entity = new EstadosEntity();
        Long id = 2L;
        String nombre = "RECHAZADO";
        String descripcion = "Rechazado por el sistema";

        entity.setIdEstado(id);
        entity.setNombre(nombre);
        entity.setDescripcion(descripcion);

        org.junit.jupiter.api.Assertions.assertEquals(id, entity.getIdEstado());
        org.junit.jupiter.api.Assertions.assertEquals(nombre, entity.getNombre());
        org.junit.jupiter.api.Assertions.assertEquals(descripcion, entity.getDescripcion());
    }
}
