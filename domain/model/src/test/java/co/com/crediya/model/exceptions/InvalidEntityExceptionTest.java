package co.com.crediya.model.exceptions;

class InvalidEntityExceptionTest {

    @org.junit.jupiter.api.Test
    void constructorSoloMensaje() {
        String mensaje = "error";
        InvalidEntityException ex = new InvalidEntityException(mensaje);
        org.junit.jupiter.api.Assertions.assertEquals(mensaje, ex.getMessage());
    }

    @org.junit.jupiter.api.Test
    void constructorMensajeYCausa() {
        String mensaje = "error";
        Throwable causa = new RuntimeException("causa");
        InvalidEntityException ex = new InvalidEntityException(mensaje, causa);
        org.junit.jupiter.api.Assertions.assertEquals(mensaje, ex.getMessage());
        org.junit.jupiter.api.Assertions.assertEquals(causa, ex.getCause());
    }
}
