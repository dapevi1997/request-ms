package co.com.crediya.config;

import co.com.bancolombia.secretsmanager.api.GenericManagerAsync;
import co.com.bancolombia.secretsmanager.api.exceptions.SecretException;
import co.com.bancolombia.secretsmanager.config.AWSSecretsManagerConfig;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnectorAsync;
import co.com.crediya.api.security.util.JwtProperties;
import co.com.crediya.config.dto.JwtSecretDto;
import co.com.crediya.config.dto.MySqlSecretDto;
import co.com.crediya.r2dbc.config.MySqlConnectionProperties;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretsConfig {
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${adapters.r2dbc.host}")
    private String host;
    @Value("${adapters.r2dbc.port}")
    private Integer port;
    @Value("${adapters.r2dbc.database}")
    private String database;

    @Bean
    @Profile("dock")
    public MySqlConnectionProperties mySqlConnectionProperties(GenericManagerAsync secretManager) throws SecretException {
        MySqlSecretDto secretDto = secretManager.getSecret("rds!db-6ad58c42-8de1-430f-82a2-11d215f5c460", MySqlSecretDto.class)
                .block();
        assert secretDto != null;
        return new MySqlConnectionProperties(host, port, database, "", secretDto.getUsername(), secretDto.getPassword());

    }

    @Bean
    @Profile("dock")
    public JwtProperties jwtProperties(GenericManagerAsync secretManager) throws SecretException {
        JwtSecretDto secret = secretManager.getSecret("jwt", JwtSecretDto.class).block();
        assert secret != null;
        return new JwtProperties(secret.getJwtSecretValue(), expiration);
    }

    @Bean
    public GenericManagerAsync getSecretManager(@Value("${aws.region}") String region) {
        return new AWSSecretManagerConnectorAsync(getConfig(region));
    }

    private AWSSecretsManagerConfig getConfig(String region) {
        return AWSSecretsManagerConfig.builder()
                .region(Region.of(region))
                .cacheSize(5)
                .cacheSeconds(3600)
                .build();
    }
}
