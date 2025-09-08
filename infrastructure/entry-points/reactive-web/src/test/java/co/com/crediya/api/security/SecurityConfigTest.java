package co.com.crediya.api.security;

import co.com.crediya.api.security.util.Roles;
import co.com.crediya.api.util.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private SecurityContextRepository securityContextRepository;

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig(securityContextRepository);
    }

    @Test
    @DisplayName("Constructor debe inicializar correctamente con SecurityContextRepository")
    void constructor_ShouldInitializeCorrectly() {
        // Assert
        assertThat(securityConfig).isNotNull();
    }

    @Test
    @DisplayName("passwordEncoder debe retornar BCryptPasswordEncoder")
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        // Act
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        // Assert
        assertThat(passwordEncoder)
                .isNotNull()
                .isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    @DisplayName("passwordEncoder debe generar hash diferentes para la misma contraseña")
    void passwordEncoder_ShouldGenerateDifferentHashesForSamePassword() {
        // Arrange
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String password = "testPassword123";

        // Act
        String hash1 = passwordEncoder.encode(password);
        String hash2 = passwordEncoder.encode(password);

        // Assert
        assertThat(hash1).isNotEqualTo(hash2);
        assertThat(passwordEncoder.matches(password, hash1)).isTrue();
        assertThat(passwordEncoder.matches(password, hash2)).isTrue();
    }

    @Test
    @DisplayName("passwordEncoder debe validar contraseñas correctamente")
    void passwordEncoder_ShouldValidatePasswordsCorrectly() {
        // Arrange
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String password = "mySecretPassword";
        String wrongPassword = "wrongPassword";

        // Act
        String hash = passwordEncoder.encode(password);

        // Assert
        assertThat(passwordEncoder.matches(password, hash)).isTrue();
        assertThat(passwordEncoder.matches(wrongPassword, hash)).isFalse();
    }

    @Test
    @DisplayName("securityWebFilterChain debe retornar configuración válida")
    void securityWebFilterChain_ShouldReturnValidConfiguration() {
        // Arrange
        ServerHttpSecurity http = ServerHttpSecurity.http();

        // Act
        SecurityWebFilterChain filterChain = securityConfig.securityWebFilterChain(http);

        // Assert
        assertThat(filterChain).isNotNull();
    }

    @Test
    @DisplayName("SecurityConfig debe tener anotación @Configuration")
    void securityConfig_ShouldHaveConfigurationAnnotation() {
        // Assert
        assertThat(securityConfig.getClass().isAnnotationPresent(org.springframework.context.annotation.Configuration.class))
                .isTrue();
    }

    @Test
    @DisplayName("SecurityConfig debe tener anotación @EnableWebFluxSecurity")
    void securityConfig_ShouldHaveEnableWebFluxSecurityAnnotation() {
        // Assert
        assertThat(securityConfig.getClass().isAnnotationPresent(org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity.class))
                .isTrue();
    }

    @Test
    void constantes_ShouldHaveRegisterUserUrlDefined() {
        // Assert
        assertThat(Constantes.URL_SOLICITAR_CREDITO).isEqualTo("/api/v1/solicitud");
    }

    @Test
    @DisplayName("Roles enum debe contener ADMIN, ASESOR y CLIENT")
    void roles_ShouldContainExpectedValues() {
        // Assert
        assertThat(Roles.ADMIN).isNotNull();
        assertThat(Roles.ASESOR).isNotNull();
        assertThat(Roles.CLIENT).isNotNull();

        // Verificar que hay exactamente 3 roles
        assertThat(Roles.values()).hasSize(3);

        // Verificar nombres de roles
        assertThat(Roles.ADMIN.name()).isEqualTo("ADMIN");
        assertThat(Roles.ASESOR.name()).isEqualTo("ASESOR");
        assertThat(Roles.CLIENT.name()).isEqualTo("CLIENT");
    }

    @Test
    @DisplayName("passwordEncoder bean debe ser reutilizable")
    void passwordEncoder_BeanShouldBeReusable() {
        // Act
        PasswordEncoder encoder1 = securityConfig.passwordEncoder();
        PasswordEncoder encoder2 = securityConfig.passwordEncoder();

        // Assert
        assertThat(encoder1)
                .isNotNull();
        assertThat(encoder2)
                .isNotNull();
        // Aunque sean diferentes instancias, deben ser del mismo tipo
        assertThat(encoder1.getClass()).isEqualTo(encoder2.getClass());
    }

    @Test
    @DisplayName("BCryptPasswordEncoder debe usar configuración por defecto")
    void passwordEncoder_ShouldUseBCryptDefaults() {
        // Arrange
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String testPassword = "testPassword";

        // Act
        String encoded = passwordEncoder.encode(testPassword);

        // Assert
        // BCrypt produce hashes que empiezan con $2a$, $2b$, o $2y$
        assertThat(encoded).matches("^\\$2[ayb]\\$.*");
        assertThat(encoded.length()).isGreaterThan(50); // BCrypt hashes son largos
    }

    @Test
    @DisplayName("securityWebFilterChain debe configurar múltiples parámetros")
    void securityWebFilterChain_ShouldConfigureMultipleParameters() {
        // Arrange
        ServerHttpSecurity http = ServerHttpSecurity.http();

        // Act
        SecurityWebFilterChain filterChain = securityConfig.securityWebFilterChain(http);

        // Assert
        assertThat(filterChain).isNotNull();
        // Verificar que el filter chain tiene filtros configurados
        assertThat(filterChain.getWebFilters()).isNotNull();
    }

    @Test
    @DisplayName("SecurityConfig debe manejar inyección de dependencias correctamente")
    void securityConfig_ShouldHandleDependencyInjection() {
        // Arrange & Act
        SecurityConfig configWithMockRepo = new SecurityConfig(securityContextRepository);

        // Assert
        assertThat(configWithMockRepo).isNotNull();
        // Verificar que puede crear beans sin errores
        assertThat(configWithMockRepo.passwordEncoder()).isNotNull();
    }
}
