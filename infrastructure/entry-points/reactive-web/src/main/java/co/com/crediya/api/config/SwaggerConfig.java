package co.com.crediya.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "API microservicio SOLICITUDES",
        version = "1.0",
        contact = @Contact(
                email = "dapevi97@gmail.com",
                name = "DANIEL PEREZ VITOLA"
        ),
        description = "Microservicio encargado de la recepción de solicitudes de préstamo"
))
public class SwaggerConfig {
}
