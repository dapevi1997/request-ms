package co.com.crediya.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Info general de la API
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("API microservicio SOLICITUDES")
                        .version("1.0")
                        .description("Microservicio encargado de las solicitudes de crédito.")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("DANIEL PEREZ VITOLA")
                                .email("dapevi97@gmail.com")))
                // Define seguridad global con bearerAuth
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
