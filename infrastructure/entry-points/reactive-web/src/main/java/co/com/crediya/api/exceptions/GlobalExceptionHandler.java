package co.com.crediya.api.exceptions;

import co.com.crediya.api.dto.ErrorResponseDto;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode;
    private final HttpStatus defaultStatus;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties webProperties, ServerCodecConfigurer codecConfigurer,
                                    ApplicationContext applicationContext, Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode,
                                    HttpStatus defaultStatus) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        this.exceptionToStatusCode = exceptionToStatusCode;
        this.defaultStatus = defaultStatus;

        this.setMessageWriters(codecConfigurer.getWriters());
        this.setMessageReaders(codecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = getError(request);
        HttpStatus httpStatus;
        switch (error.getClass().getSimpleName()) {
            case "InvalidEntityException":
            case "BadRequestException":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case "DomainException":
                httpStatus = HttpStatus.CONFLICT;
                break;
            default:
                if (error instanceof Exception) {
                    httpStatus = exceptionToStatusCode.getOrDefault(error.getClass(), defaultStatus);
                } else {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                }
                break;
        }
        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new ErrorResponseDto(error.getMessage(), httpStatus.toString()))
                );
    }
}
