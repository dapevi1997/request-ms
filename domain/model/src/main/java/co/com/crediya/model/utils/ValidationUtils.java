package co.com.crediya.model.utils;

import co.com.crediya.model.exceptions.InvalidEntityException;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidationUtils {
    
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private ValidationUtils() {
        // Utility class - private constructor
    }
    
    public static void validateNotNullString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidEntityException(fieldName + " no puede ser nulo o vacío");
        }
    }
    
    public static void validateNotNullLong(Long value, String fieldName) {
        if (value == null) {
            throw new InvalidEntityException(fieldName + " no puede ser nulo");
        }
    }
    
    public static void validatePositiveLong(Long value, String fieldName) {
        validateNotNullLong(value, fieldName);
        if (value <= 0) {
            throw new InvalidEntityException(fieldName + " debe ser mayor a cero");
        }
    }
    
    public static void validateNotNullBigDecimal(BigDecimal value, String fieldName) {
        if (value == null) {
            throw new InvalidEntityException(fieldName + " no puede ser nulo");
        }
    }
    
    public static void validatePositiveBigDecimal(BigDecimal value, String fieldName) {
        validateNotNullBigDecimal(value, fieldName);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidEntityException(fieldName + " debe ser mayor a cero");
        }
    }
    
    public static void validateNonNegativeBigDecimal(BigDecimal value, String fieldName) {
        validateNotNullBigDecimal(value, fieldName);
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidEntityException(fieldName + " no puede ser negativo");
        }
    }
    
    public static void validateNotNullBoolean(Boolean value, String fieldName) {
        if (value == null) {
            throw new InvalidEntityException(fieldName + " no puede ser nulo");
        }
    }
    
    public static void validateNotNullInteger(Integer value, String fieldName) {
        if (value == null) {
            throw new InvalidEntityException(fieldName + " no puede ser nulo");
        }
    }
    
    public static void validatePositiveInteger(Integer value, String fieldName) {
        validateNotNullInteger(value, fieldName);
        if (value <= 0) {
            throw new InvalidEntityException(fieldName + " debe ser mayor a cero");
        }
    }
    
    public static void validateEmail(String email, String fieldName) {
        validateNotNullString(email, fieldName);
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEntityException(fieldName + " debe tener un formato válido");
        }
    }
}
