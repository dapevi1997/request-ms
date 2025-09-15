package co.com.crediya.model.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonMapperExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String expectedMessage = "Error parsing JSON";

        // Act
        JsonMapperException exception = new JsonMapperException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        // Arrange
        String expectedMessage = "Error parsing JSON with cause";
        Throwable expectedCause = new RuntimeException("Root cause");

        // Act
        JsonMapperException exception = new JsonMapperException(expectedMessage, expectedCause);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
    }
}
