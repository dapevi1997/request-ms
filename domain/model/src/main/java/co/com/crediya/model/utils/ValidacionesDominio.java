package co.com.crediya.model.utils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;
import co.com.crediya.model.exceptions.InvalidEntityException;

public class ValidacionesDominio {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static void validarNoNullOVacio(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidEntityException(fieldName + " no puede ser nulo o vacío");
        }
    }

    public static void validarPositivo(Long value, String fieldName) {
        validarNull(value, fieldName);
        if (value <= 0) {
            throw new InvalidEntityException(fieldName + " debe ser mayor a cero");
        }
    }

    public static void validarNoNegativo(BigDecimal value, String fieldName) {
        validarNull(value, fieldName);
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidEntityException(fieldName + " no puede ser negativo");
        }
    }

    public static void validarNull(Object value, String fieldName) {
        if (Objects.isNull(value)) {
            throw new InvalidEntityException(fieldName + " no puede ser nulo");
        }
    }

    public static void validarPositivo(Integer value, String fieldName) {
        validarNull(value, fieldName);
        if (value <= 0) {
            throw new InvalidEntityException(fieldName + " debe ser mayor a cero");
        }
    }

    public static void validarEmail(String email, String fieldName) {
        validarNoNullOVacio(email, fieldName);
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEntityException(fieldName + " debe tener un formato válido");
        }
    }

    public static void validarMontos(BigDecimal montoMinimo, BigDecimal montoMaximo) {
        if (montoMinimo.compareTo(montoMaximo) > 0) {
            throw new InvalidEntityException("El monto mínimo no puede ser mayor que el monto máximo");
        }
    }
}
