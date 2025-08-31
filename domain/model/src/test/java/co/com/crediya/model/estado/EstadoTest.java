package co.com.crediya.model.estado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import co.com.crediya.model.exceptions.InvalidEntityException;
import org.junit.jupiter.api.Test;

class EstadoTest {
    @Test
    void deberiaCrearEstadoCorrectamenteCuandoTodosLosParametrosSonValidos() {
        // Arrange
        Long idEstado = 1L;
        String nombre = "PENDIENTE";
        String descripcion = "Estado pendiente de revisión";

        // Act
        Estado estado = new Estado(idEstado, nombre, descripcion);

        // Assert
        assertNotNull(estado);
        assertEquals(idEstado, estado.getIdEstado());
        assertEquals(nombre, estado.getNombre());
        assertEquals(descripcion, estado.getDescripcion());
    }

    @Test
    void deberiaLanzarExcepcionCuandoIdEstadoEsNulo() {
        // Arrange
        Long idEstado = null;
        String nombre = "PENDIENTE";
        String descripcion = "Estado pendiente de revisión";

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Estado(idEstado, nombre, descripcion);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoIdEstadoEsNegativo() {
        // Arrange
        Long idEstado = -1L;
        String nombre = "PENDIENTE";
        String descripcion = "Estado pendiente de revisión";

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Estado(idEstado, nombre, descripcion);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoNombreEsNulo() {
        // Arrange
        Long idEstado = 1L;
        String nombre = null;
        String descripcion = "Estado pendiente de revisión";

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Estado(idEstado, nombre, descripcion);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoNombreEsVacio() {
        // Arrange
        Long idEstado = 1L;
        String nombre = "";
        String descripcion = "Estado pendiente de revisión";

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Estado(idEstado, nombre, descripcion);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoDescripcionEsNulo() {
        // Arrange
        Long idEstado = 1L;
        String nombre = "PENDIENTE";
        String descripcion = null;

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Estado(idEstado, nombre, descripcion);
        });
    }

    @Test
    void deberiaLanzarExcepcionCuandoDescripcionEsVacio() {
        // Arrange
        Long idEstado = 1L;
        String nombre = "PENDIENTE";
        String descripcion = "";

        // Act & Assert
        assertThrows(InvalidEntityException.class, () -> {
            new Estado(idEstado, nombre, descripcion);
        });
    }

    @Test
    void deberiaPermitirModificarIdEstadoConSetter() {
        // Arrange
        Estado estado = new Estado();
        Long nuevoIdEstado = 2L;

        // Act
        estado.setIdEstado(nuevoIdEstado);

        // Assert
        assertEquals(nuevoIdEstado, estado.getIdEstado());
    }

    @Test
    void deberiaPermitirModificarNombreConSetter() {
        // Arrange
        Estado estado = new Estado();
        String nuevoNombre = "APROBADO";

        // Act
        estado.setNombre(nuevoNombre);

        // Assert
        assertEquals(nuevoNombre, estado.getNombre());
    }

    @Test
    void deberiaPermitirModificarDescripcionConSetter() {
        // Arrange
        Estado estado = new Estado();
        String nuevaDescripcion = "Estado aprobado correctamente";

        // Act
        estado.setDescripcion(nuevaDescripcion);

        // Assert
        assertEquals(nuevaDescripcion, estado.getDescripcion());
    }

    @Test
    void deberiaGenerarToStringCorrectamenteCuandoValoresSonNulos() {
        // Arrange
        Estado estado = new Estado();
        String expectedToString = "Estados{idEstado=null, nombre='null', descripcion='null'}";

        // Act
        String actualToString = estado.toString();

        // Assert
        assertEquals(expectedToString, actualToString);
    }
}

