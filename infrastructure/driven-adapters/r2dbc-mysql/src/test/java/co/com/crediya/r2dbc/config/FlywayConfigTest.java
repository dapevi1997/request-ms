package co.com.crediya.r2dbc.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

@ExtendWith(MockitoExtension.class)
class FlywayConfigTest {

    @Test
    @DisplayName("Debe ejecutar flyway.migrate() con los parámetros correctos")
    void testMigrateFlyway() {
        // Arrange
        var properties = mock(MySqlConnectionProperties.class);
        when(properties.host()).thenReturn("localhost");
        when(properties.port()).thenReturn(3306);
        when(properties.database()).thenReturn("testdb");
        when(properties.username()).thenReturn("user");
        when(properties.password()).thenReturn("pass");

        var flywayMock = mock(Flyway.class);


        try (MockedStatic<Flyway> flywayStatic = mockStatic(Flyway.class, CALLS_REAL_METHODS)) {
            var fluentConfig = Flyway.configure();
            var configSpy = spy(fluentConfig);
            flywayStatic.when(Flyway::configure).thenReturn(configSpy);

            doReturn(configSpy).when(configSpy).dataSource(anyString(), anyString(), anyString());
            doReturn(configSpy).when(configSpy).locations(anyString());
            doReturn(configSpy).when(configSpy).baselineOnMigrate(anyBoolean());
            doReturn(configSpy).when(configSpy).baselineVersion(anyString());
            doReturn(configSpy).when(configSpy).schemas(anyString());
            doReturn(flywayMock).when(configSpy).load();

            FlywayConfig config = new FlywayConfig();
            CommandLineRunner runner = config.migrateFlyway(properties);

            // Act
            assertDoesNotThrow(() -> runner.run(new String[] {}));

            // Assert
            verify(flywayMock, times(1)).migrate();
        }
    }
}
