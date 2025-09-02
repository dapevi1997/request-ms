package co.com.crediya.model.utils;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.exceptions.InvalidEntityException;

class ValidacionesDominioTest {

    // --- validarNoNullOVacio ---
    @Test
    void validarNoNullOVacioNoLanzaExcepcionConValorValido() {
        // Arrange & Act & Assert
        ValidacionesDominio.validarNoNullOVacio("abc", "campo");
    }

    @Test
    void validarNoNullOVacioLanzaExcepcionConNull() {
        // Arrange & Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarNoNullOVacio(null, "campo"));
    }

    @Test
    void validarNoNullOVacioLanzaExcepcionConVacio() {
        // Arrange & Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarNoNullOVacio("   ", "campo"));
    }

    // --- validarPositivo(Long) ---
    @Test
    void validarPositivoLongValido() {
        ValidacionesDominio.validarPositivo(5L, "campo");
    }

    @Test
    void validarPositivoLongLanzaExcepcionSiEsCeroONegativo() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarPositivo(0L, "campo"));
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarPositivo(-1L, "campo"));
    }

    // --- validarNoNegativo ---
    @Test
    void validarNoNegativoValido() {
        ValidacionesDominio.validarNoNegativo(BigDecimal.ZERO, "campo");
        ValidacionesDominio.validarNoNegativo(new BigDecimal("10"), "campo");
    }

    @Test
    void validarNoNegativoLanzaExcepcionSiEsNegativo() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarNoNegativo(new BigDecimal("-1"), "campo"));
    }

    @Test
    void validarNoNegativoLanzaExcepcionSiEsNull() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarNoNegativo(null, "campo"));
    }

    // --- validarNull ---
    @Test
    void validarNullLanzaExcepcionSiEsNull() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarNull(null, "campo"));
    }

    @Test
    void validarNullNoLanzaExcepcionSiNoEsNull() {
        ValidacionesDominio.validarNull("valor", "campo");
    }

    // --- validarPositivo(Integer) ---
    @Test
    void validarPositivoIntegerValido() {
        ValidacionesDominio.validarPositivo(1, "campo");
    }

    @Test
    void validarPositivoIntegerLanzaExcepcionSiEsCeroONegativo() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarPositivo(0, "campo"));
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarPositivo(-1, "campo"));
    }


    @Test
    void validarEmailValido() {
        ValidacionesDominio.validarEmail("test@email.com", "campo");
    }

    @Test
    void validarEmailLanzaExcepcionSiEsNullOVacio() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarEmail(null, "campo"));
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarEmail("", "campo"));
    }

    @Test
    void validarEmailLanzaExcepcionSiFormatoInvalido() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarEmail("noemail", "campo"));
    }

    // --- validarMontos ---
    @Test
    void validarMontosValido() {
        ValidacionesDominio.validarMontos(new BigDecimal("1"), new BigDecimal("2"));
        ValidacionesDominio.validarMontos(new BigDecimal("2"), new BigDecimal("2"));
    }

    @Test
    void validarMontosLanzaExcepcionSiMinimoMayorQueMaximo() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEntityException.class,
                () -> ValidacionesDominio.validarMontos(new BigDecimal("3"), new BigDecimal("2")));
    }
}
