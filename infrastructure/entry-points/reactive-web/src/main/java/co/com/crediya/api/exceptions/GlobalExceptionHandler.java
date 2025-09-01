package co.com.crediya.api.exceptions;

import co.com.crediya.api.dto.ErrorResponseDto;
import co.com.crediya.model.exceptions.DomainException;
import co.com.crediya.model.exceptions.InvalidEntityException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final Map<Class<? extends Throwable>, HttpStatus> exceptionToHttpStatus = new HashMap<>();

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties webProperties, ServerCodecConfigurer codecConfigurer,
                                    ApplicationContext applicationContext) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
        this.setMessageReaders(codecConfigurer.getReaders());

        exceptionToHttpStatus.put(DomainException.class, HttpStatus.CONFLICT);
        exceptionToHttpStatus.put(InvalidEntityException.class, HttpStatus.BAD_REQUEST);
        exceptionToHttpStatus.put(ServerWebInputException.class, HttpStatus.BAD_REQUEST);
        exceptionToHttpStatus.put(BadRequestException.class, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = getError(request);

        HttpStatus httpStatus = Optional.ofNullable(exceptionToHttpStatus.get(error.getClass()))
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ErrorResponseDto.builder()
                        .message(error.getMessage())
                        .httpStatus(httpStatus.getReasonPhrase())
                        .build())
                );
    }
}
