package co.com.crediya.api.openapi;

import co.com.crediya.api.dto.ErrorResponseDto;
import co.com.crediya.api.dto.SolicitudRequestDto;
import co.com.crediya.api.dto.SolicitudResponseDto;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springdoc.core.fn.builders.operation.Builder;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class SolicitudesOpenApi {
    private final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String CONFLICT_CODE = String.valueOf(HttpStatus.CONFLICT.value());
    private final String INTERNAL_ERROR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    public Builder registroSolicitudPrestamo(Builder builder) {
        return builder
                .operationId("registroSolicitudPrestamo")
                .tag("Solicitudes de préstamo")
                .summary("Registrar una nueva solicitud de préstamo")

                // requestBody
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .description("Json asociado a la request")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(SolicitudRequestDto.class))))

                // 201 Created
                .response(responseBuilder()
                        .responseCode(CREATED_CODE)
                        .description("Solicitud creada correctamente")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(SolicitudResponseDto.class))))

                // 400 Bad Request
                .response(responseBuilder()
                        .responseCode(BAD_REQUEST_CODE)
                        .description("Error en la solicitud")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponseDto.class))))

                // 409 Conflict
                .response(responseBuilder()
                        .responseCode(CONFLICT_CODE)
                        .description("Conflicto al registrar la solicitud")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponseDto.class))))

                // 500 Internal Server Error
                .response(responseBuilder()
                        .responseCode(INTERNAL_ERROR_CODE)
                        .description("Error interno del servidor")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponseDto.class))));
    }
}
