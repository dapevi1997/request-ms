package co.com.crediya.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OpenApiConfigTest {

    private OpenApiConfig openApiConfig;

    OpenApiConfigTest() {
    }

    @BeforeEach
    void setUp() {
        openApiConfig = new OpenApiConfig();
    }

    @Test
    @DisplayName("Debería crear configuración OpenAPI correctamente")
    void deberiaCrearConfiguracionOpenAPICorrectamente() {
        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assert openAPI != null;
        assert openAPI.getInfo() != null;
        assert "API microservicio SOLICITUDES".equals(openAPI.getInfo().getTitle());
        assert "1.0".equals(openAPI.getInfo().getVersion());
    }

    @Test
    @DisplayName("Debería configurar información de contacto correctamente")
    void deberiaConfigurarInformacionDeContactoCorrectamente() {
        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assert openAPI.getInfo().getContact() != null;
        assert "DANIEL PEREZ VITOLA".equals(openAPI.getInfo().getContact().getName());
        assert "dapevi97@gmail.com".equals(openAPI.getInfo().getContact().getEmail());
    }

    @Test
    @DisplayName("Debería configurar seguridad global con bearerAuth")
    void deberiaConfigurarSeguridadGlobalConBearerAuth() {
        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assert openAPI.getSecurity() != null;
        assert openAPI.getSecurity().size() == 1;
        assert openAPI.getSecurity().get(0).get("bearerAuth") != null;
    }

    @Test
    @DisplayName("Debería configurar esquema de seguridad bearerAuth correctamente")
    void deberiaConfigurarEsquemaDeSeguridadBearerAuthCorrectamente() {
        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        var bearerAuthScheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");

        // Assert
        assert bearerAuthScheme != null;
        assert "bearerAuth".equals(bearerAuthScheme.getName());
        assert "bearer".equals(bearerAuthScheme.getScheme());
        assert "JWT".equals(bearerAuthScheme.getBearerFormat());
 
   }
}