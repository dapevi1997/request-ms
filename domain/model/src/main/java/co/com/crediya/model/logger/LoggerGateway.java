package co.com.crediya.model.logger;

public interface LoggerGateway {
    void info(String message, Object... arguments);
    void error(String message, Object... arguments);
}
