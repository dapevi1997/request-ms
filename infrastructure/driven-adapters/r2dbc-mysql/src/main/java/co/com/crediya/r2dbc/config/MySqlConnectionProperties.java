package co.com.crediya.r2dbc.config;

public record MySqlConnectionProperties(
        String host,
        Integer port,
        String database,
        String schema,
        String username,
        String password) {
}