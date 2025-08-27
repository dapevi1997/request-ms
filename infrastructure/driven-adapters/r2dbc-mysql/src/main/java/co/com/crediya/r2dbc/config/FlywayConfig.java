package co.com.crediya.r2dbc.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    public CommandLineRunner migrateFlyway(MySqlConnectionProperties properties){
        return args -> {
            String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s?createDatabaseIfNotExist=true",
                    properties.host(),
                    properties.port(),
                    properties.database());

            Flyway flyway = Flyway.configure()
                    .dataSource(jdbcUrl, properties.username(), properties.password())
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .baselineVersion("0")
                    .schemas(properties.database())
                    .load();

            flyway.migrate();
        };
    }
}
