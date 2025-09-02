package co.com.crediya.api.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Test
    @DisplayName("Debería ser un componente de manejo de excepciones web")
    void deberiaSerUnComponenteDeManejoDeExcepcionesWeb() {
        // Act & Assert
        assertThat(GlobalExceptionHandler.class
                .getAnnotation(org.springframework.stereotype.Component.class)).isNotNull();
        assertThat(GlobalExceptionHandler.class
                .getAnnotation(org.springframework.core.annotation.Order.class)).isNotNull();
        assertThat(GlobalExceptionHandler.class
                .getAnnotation(org.springframework.core.annotation.Order.class).value())
                        .isEqualTo(-2);
    }

    @Test
    @DisplayName("Debería heredar de AbstractErrorWebExceptionHandler")
    void deberiaHeredarDeAbstractErrorWebExceptionHandler() {
        // Act & Assert
        assertThat(GlobalExceptionHandler.class.getSuperclass().getSimpleName())
                .isEqualTo("AbstractErrorWebExceptionHandler");
    }

    @Test
    @DisplayName("Debería tener constructor con las dependencias correctas")
    void deberiaTenerConstructorConLasDependenciasCorrectas() {
        // Arrange & Act
        var constructors = GlobalExceptionHandler.class.getConstructors();
        var constructor = constructors[0];
        var parameterTypes = constructor.getParameterTypes();

        // Assert
        assertThat(constructors).hasSize(1);
        assertThat(parameterTypes).hasSize(4);
        assertThat(parameterTypes[0].getSimpleName()).isEqualTo("ErrorAttributes");
        assertThat(parameterTypes[1].getSimpleName()).isEqualTo("WebProperties");
        assertThat(parameterTypes[2].getSimpleName()).isEqualTo("ServerCodecConfigurer");
        assertThat(parameterTypes[3].getSimpleName()).isEqualTo("ApplicationContext");
    }

    @Test
    @DisplayName("Debería ser una clase pública")
    void deberiaSerUnaClasePublica() {
        // Act & Assert
        assertThat(GlobalExceptionHandler.class.getModifiers() & java.lang.reflect.Modifier.PUBLIC)
                .isNotZero();
    }
}
