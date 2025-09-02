package co.com.crediya.r2dbc.entity;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SolicitudEntityTest {

    @Test
    void constructorYGettersFuncionanCorrectamente() {
        Long id = 1L;
        BigDecimal monto = new BigDecimal("5000");
        Integer plazo = 12;
        String email = "test@correo.com";
        String doc = "123456";
        Long idEstado = 2L;
        Long idTipoPrestamo = 3L;

        SolicitudEntity entity =
                new SolicitudEntity(id, monto, plazo, email, idEstado, idTipoPrestamo);
        org.junit.jupiter.api.Assertions.assertEquals(id, entity.getIdSolicitud());
        org.junit.jupiter.api.Assertions.assertEquals(monto, entity.getMonto());
        org.junit.jupiter.api.Assertions.assertEquals(plazo, entity.getPlazo());
        org.junit.jupiter.api.Assertions.assertEquals(email, entity.getEmail());
        org.junit.jupiter.api.Assertions.assertEquals(idEstado, entity.getIdEstado());
        org.junit.jupiter.api.Assertions.assertEquals(idTipoPrestamo, entity.getIdTipoPrestamo());
    }

    @Test
    void settersFuncionanCorrectamente() {
        SolicitudEntity entity = new SolicitudEntity();
        Long id = 2L;
        BigDecimal monto = new BigDecimal("10000");
        Integer plazo = 24;
        String email = "otro@correo.com";
        String doc = "654321";
        Long idEstado = 4L;
        Long idTipoPrestamo = 5L;

        entity.setIdSolicitud(id);
        entity.setMonto(monto);
        entity.setPlazo(plazo);
        entity.setEmail(email);
        entity.setDocumentoIdentidad(doc);
        entity.setIdEstado(idEstado);
        entity.setIdTipoPrestamo(idTipoPrestamo);

        org.junit.jupiter.api.Assertions.assertEquals(id, entity.getIdSolicitud());
        org.junit.jupiter.api.Assertions.assertEquals(monto, entity.getMonto());
        org.junit.jupiter.api.Assertions.assertEquals(plazo, entity.getPlazo());
        org.junit.jupiter.api.Assertions.assertEquals(email, entity.getEmail());
        org.junit.jupiter.api.Assertions.assertEquals(doc, entity.getDocumentoIdentidad());
        org.junit.jupiter.api.Assertions.assertEquals(idEstado, entity.getIdEstado());
        org.junit.jupiter.api.Assertions.assertEquals(idTipoPrestamo, entity.getIdTipoPrestamo());
    }
}
