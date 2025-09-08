package co.com.crediya.api.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private ErrorAttributes errorAttributes;

    @Mock
    private WebProperties webProperties;

    @Mock
    private ServerCodecConfigurer codecConfigurer;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private WebProperties.Resources resources;

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        when(webProperties.getResources()).thenReturn(resources);
        when(codecConfigurer.getWriters()).thenReturn(List.of());
        when(codecConfigurer.getReaders()).thenReturn(List.of());

        // Mock del ApplicationContext con classLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        when(applicationContext.getClassLoader()).thenReturn(classLoader);

        globalExceptionHandler = new GlobalExceptionHandler(errorAttributes, webProperties,
                codecConfigurer, applicationContext);
    }

    @Test
    @DisplayName("Constructor debe inicializar correctamente sin lanzar excepciones")
    void constructor_ShouldInitializeCorrectly() {
        // Assert
        assertThat(globalExceptionHandler).isNotNull();
    }

    @Test
    @DisplayName("getRoutingFunction debe retornar RouterFunction válida")
    void getRoutingFunction_ShouldReturnValidRouterFunction() {
        // Act
        RouterFunction<ServerResponse> routingFunction =
                globalExceptionHandler.getRoutingFunction(errorAttributes);

        // Assert
        assertThat(routingFunction).isNotNull();
    }

    @Test
    @DisplayName("GlobalExceptionHandler debe extender AbstractErrorWebExceptionHandler")
    void globalExceptionHandler_ShouldExtendAbstractErrorWebExceptionHandler() {
        // Assert
        assertThat(globalExceptionHandler).isInstanceOf(
                org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler.class);
    }

    @Test
    @DisplayName("GlobalExceptionHandler debe tener anotación @Component")
    void globalExceptionHandler_ShouldHaveComponentAnnotation() {
        // Assert
        assertThat(globalExceptionHandler.getClass()
                .isAnnotationPresent(org.springframework.stereotype.Component.class)).isTrue();
    }

    @Test
    @DisplayName("GlobalExceptionHandler debe tener anotación @Order con valor -2")
    void globalExceptionHandler_ShouldHaveOrderAnnotation() {
        // Act
        org.springframework.core.annotation.Order orderAnnotation = globalExceptionHandler
                .getClass().getAnnotation(org.springframework.core.annotation.Order.class);

        // Assert
        assertThat(orderAnnotation).isNotNull();
        assertThat(orderAnnotation.value()).isEqualTo(-2);
    }
}
