package co.com.crediya.model.exceptions;

class DomainExceptionTest {

    @org.junit.jupiter.api.Test
    void constructorMensaje() {
        String mensaje = "error dominio";
        DomainException ex = new DomainException(mensaje);
        org.junit.jupiter.api.Assertions.assertEquals(mensaje, ex.getMessage());
    }
}
