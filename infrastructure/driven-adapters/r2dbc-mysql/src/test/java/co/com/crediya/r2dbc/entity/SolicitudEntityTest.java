package co.com.crediya.r2dbc.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolicitudEntityTest {

    @Test
    void constructorYGettersFuncionanCorrectamente() {
        Long id = 1L;
        BigDecimal monto = new BigDecimal("5000");
        Integer plazo = 12;
        String email = "test@correo.com";
        Long idEstado = 2L;
        Long idTipoPrestamo = 3L;
        LocalDate createdAt = LocalDate.now();

        SolicitudEntity entity =
                new SolicitudEntity(id, monto, plazo, email, idEstado, idTipoPrestamo, createdAt);
        assertEquals(id, entity.getIdSolicitud());
        assertEquals(monto, entity.getMonto());
        assertEquals(plazo, entity.getPlazo());
        assertEquals(email, entity.getEmail());
        assertEquals(idEstado, entity.getIdEstado());
        assertEquals(idTipoPrestamo, entity.getIdTipoPrestamo());
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
        entity.setIdEstado(idEstado);
        entity.setIdTipoPrestamo(idTipoPrestamo);

        assertEquals(id, entity.getIdSolicitud());
        assertEquals(monto, entity.getMonto());
        assertEquals(plazo, entity.getPlazo());
        assertEquals(email, entity.getEmail());
        assertEquals(idEstado, entity.getIdEstado());
        assertEquals(idTipoPrestamo, entity.getIdTipoPrestamo());
    }
}
