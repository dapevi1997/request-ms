package co.com.crediya.api.swaggerutil;

import co.com.crediya.api.dto.ErrorResponseDto;
import co.com.crediya.api.dto.SolicitarPrestamoRequestDto;
import co.com.crediya.api.dto.SolicitarPrestamoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OpenApiDocs {
    @Operation(
            operationId = "registerUser",
            summary = "Registrar un nuevo usuario",
            requestBody = @RequestBody(
                    useParameterTypeSchema = true,
                    description = "Json asociado a la request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SolicitarPrestamoRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Usuario registrado correctamente",
                            content = @Content(schema = @Schema(implementation = SolicitarPrestamoResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Error en la solicitud",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409", description = "Conflicto al registrar el usuario",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Error interno del servidor",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )
    public Mono<ServerResponse> solicitarPrestamoOperacion(ServerRequest request) {
        return null;
    }
}
