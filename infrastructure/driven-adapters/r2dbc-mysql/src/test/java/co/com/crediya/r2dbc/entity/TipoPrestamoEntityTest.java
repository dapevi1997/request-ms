package co.com.crediya.r2dbc.entity;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class TipoPrestamoEntityTest {

    @Test
    void constructorYGettersFuncionanCorrectamente() {
        Long id = 1L;
        String nombre = "Personal";
        BigDecimal max = new BigDecimal("10000");
        BigDecimal min = new BigDecimal("1000");
        BigDecimal tasa = new BigDecimal("5.5");
        Boolean validacion = true;

        TipoPrestamoEntity entity = new TipoPrestamoEntity(id, nombre, max, min, tasa, validacion);
        org.junit.jupiter.api.Assertions.assertEquals(id, entity.getIdTipoPrestamo());
        org.junit.jupiter.api.Assertions.assertEquals(nombre, entity.getNombre());
        org.junit.jupiter.api.Assertions.assertEquals(max, entity.getMontoMaximo());
        org.junit.jupiter.api.Assertions.assertEquals(min, entity.getMontoMinimo());
        org.junit.jupiter.api.Assertions.assertEquals(tasa, entity.getTasaInteres());
        org.junit.jupiter.api.Assertions.assertEquals(validacion, entity.getValidacionAutomatica());
    }

    @Test
    void settersFuncionanCorrectamente() {
        TipoPrestamoEntity entity = new TipoPrestamoEntity();
        Long id = 2L;
        String nombre = "Empresarial";
        BigDecimal max = new BigDecimal("20000");
        BigDecimal min = new BigDecimal("2000");
        BigDecimal tasa = new BigDecimal("7.5");
        Boolean validacion = false;

        entity.setIdTipoPrestamo(id);
        entity.setNombre(nombre);
        entity.setMontoMaximo(max);
        entity.setMontoMinimo(min);
        entity.setTasaInteres(tasa);
        entity.setValidacionAutomatica(validacion);

        org.junit.jupiter.api.Assertions.assertEquals(id, entity.getIdTipoPrestamo());
        org.junit.jupiter.api.Assertions.assertEquals(nombre, entity.getNombre());
        org.junit.jupiter.api.Assertions.assertEquals(max, entity.getMontoMaximo());
        org.junit.jupiter.api.Assertions.assertEquals(min, entity.getMontoMinimo());
        org.junit.jupiter.api.Assertions.assertEquals(tasa, entity.getTasaInteres());
        org.junit.jupiter.api.Assertions.assertEquals(validacion, entity.getValidacionAutomatica());
    }
}
