package co.com.crediya.model.tipoprestamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.exceptions.InvalidEntityException;

class TipoPrestamoTest {

    @Test
    void deberiaCrearTipoPrestamoExitosamenteConDatosValidos() {
        // Arrange
        Long id = 1L;
        String nombre = "Préstamo Personal";
        BigDecimal montoMaximo = new BigDecimal("100000");
        BigDecimal montoMinimo = new BigDecimal("10000");
        BigDecimal tasaInteres = new BigDecimal("15.5");
        Boolean validacionAutomatica = true;

        // Act
        TipoPrestamo tipoPrestamo = new TipoPrestamo(id, nombre, montoMaximo, montoMinimo,
                tasaInteres, validacionAutomatica);

        // Assert
        assertNotNull(tipoPrestamo);
        assertEquals(id, tipoPrestamo.getIdTipoPrestamo());
        assertEquals(nombre, tipoPrestamo.getNombre());
        assertEquals(montoMaximo, tipoPrestamo.getMontoMaximo());
        assertEquals(montoMinimo, tipoPrestamo.getMontoMinimo());
        assertEquals(tasaInteres, tipoPrestamo.getTasaInteres());
        assertEquals(validacionAutomatica, tipoPrestamo.getValidacionAutomatica());
    }

    @Test
    void deberiaLanzarExcepcionCuandoIdEsNulo() {
        // Arrange
        Long id = null;
        String nombre = "Préstamo Personal";
        BigDecimal montoMaximo = new BigDecimal("100000");
        BigDecimal montoMinimo = new BigDecimal("10000");
        BigDecimal tasaInteres = new BigDecimal("15.5");
        Boolean validacionAutomatica = true;

        // Act & Assert
        InvalidEntityException exception = assertThrows(InvalidEntityException.class, () -> {
            new TipoPrestamo(id, nombre, montoMaximo, montoMinimo, tasaInteres,
                    validacionAutomatica);
        });

        assertEquals("El ID del tipo de préstamo no puede ser nulo", exception.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionCuandoNombreEsNuloOVacio() {
        // Arrange
        Long id = 1L;
        String nombreNulo = null;
        BigDecimal montoMaximo = new BigDecimal("100000");
        BigDecimal montoMinimo = new BigDecimal("10000");
        BigDecimal tasaInteres = new BigDecimal("15.5");
        Boolean validacionAutomatica = true;

        // Act & Assert
        InvalidEntityException exception = assertThrows(InvalidEntityException.class, () -> {
            new TipoPrestamo(id, nombreNulo, montoMaximo, montoMinimo, tasaInteres,
                    validacionAutomatica);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoMontoMinimoMayorQueMaximo() {
        // Arrange
        Long id = 1L;
        String nombre = "Préstamo Personal";
        BigDecimal montoMaximo = new BigDecimal("10000");
        BigDecimal montoMinimo = new BigDecimal("50000"); // Mayor que el máximo
        BigDecimal tasaInteres = new BigDecimal("15.5");
        Boolean validacionAutomatica = true;

        // Act & Assert
        InvalidEntityException exception = assertThrows(InvalidEntityException.class, () -> {
            new TipoPrestamo(id, nombre, montoMaximo, montoMinimo, tasaInteres,
                    validacionAutomatica);
        });

        assertEquals("El monto mínimo no puede ser mayor que el monto máximo",
                exception.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionCuandoTasaInteresEsNegativa() {
        // Arrange
        Long id = 1L;
        String nombre = "Préstamo Personal";
        BigDecimal montoMaximo = new BigDecimal("100000");
        BigDecimal montoMinimo = new BigDecimal("10000");
        BigDecimal tasaInteres = new BigDecimal("-5.0");
        Boolean validacionAutomatica = true;

        // Act & Assert
        InvalidEntityException exception = assertThrows(InvalidEntityException.class, () -> {
            new TipoPrestamo(id, nombre, montoMaximo, montoMinimo, tasaInteres,
                    validacionAutomatica);
        });

        assertEquals("La tasa de interés no puede ser negativo", exception.getMessage());
    }

    @Test
    void deberiaCrearTipoPrestamoConConstructorVacio() {
        // Arrange & Act
        TipoPrestamo tipoPrestamo = new TipoPrestamo();

        // Assert
        assertNotNull(tipoPrestamo);
        assertNull(tipoPrestamo.getIdTipoPrestamo());
        assertNull(tipoPrestamo.getNombre());
        assertNull(tipoPrestamo.getMontoMaximo());
        assertNull(tipoPrestamo.getMontoMinimo());
        assertNull(tipoPrestamo.getTasaInteres());
        assertNull(tipoPrestamo.getValidacionAutomatica());
    }

    @Test
    void gettersYSettersFuncionanCorrectamente() {
        // Arrange
        TipoPrestamo tipoPrestamo = new TipoPrestamo();
        Long id = 5L;
        String nombre = "Test";
        BigDecimal max = new BigDecimal("1000");
        BigDecimal min = new BigDecimal("100");
        BigDecimal tasa = new BigDecimal("10.5");
        Boolean validacion = false;

        // Act
        tipoPrestamo.setIdTipoPrestamo(id);
        tipoPrestamo.setNombre(nombre);
        tipoPrestamo.setMontoMaximo(max);
        tipoPrestamo.setMontoMinimo(min);
        tipoPrestamo.setTasaInteres(tasa);
        tipoPrestamo.setValidacionAutomatica(validacion);

        // Assert
        assertEquals(id, tipoPrestamo.getIdTipoPrestamo());
        assertEquals(nombre, tipoPrestamo.getNombre());
        assertEquals(max, tipoPrestamo.getMontoMaximo());
        assertEquals(min, tipoPrestamo.getMontoMinimo());
        assertEquals(tasa, tipoPrestamo.getTasaInteres());
        assertEquals(validacion, tipoPrestamo.getValidacionAutomatica());
    }
}
