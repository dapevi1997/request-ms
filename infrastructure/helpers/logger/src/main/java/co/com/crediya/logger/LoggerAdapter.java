package co.com.crediya.logger;

import co.com.crediya.model.logger.LoggerGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggerAdapter implements LoggerGateway {
    @Override
    public void info(String message, Object... arguments) {
        log.info(message, arguments);
    }

    @Override
    public void error(String message, Object... arguments) {
        log.error(message, arguments);
    }
}
