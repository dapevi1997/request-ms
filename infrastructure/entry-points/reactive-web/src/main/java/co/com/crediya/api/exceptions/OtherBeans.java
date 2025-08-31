package co.com.crediya.api.exceptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class OtherBeans {
    @Bean
    public HttpStatus defaultStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
