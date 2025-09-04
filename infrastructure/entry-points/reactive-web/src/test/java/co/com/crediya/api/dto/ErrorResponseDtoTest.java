package co.com.crediya.api.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        String status = "404";
        String message = "Not Found";
        String fixedTimestamp = "123456789";

        ErrorResponseDto dto = new ErrorResponseDto(status, message, fixedTimestamp);

        assertThat(dto.getHttpStatus()).isEqualTo(status);
        assertThat(dto.getMessage()).isEqualTo(message);
        assertThat(dto.getTimestamp()).isEqualTo(fixedTimestamp);
    }

    @Test
    void testBuilderWithDefaults() {
        ErrorResponseDto dto = ErrorResponseDto.builder()
                .httpStatus("500")
                .message("Internal Server Error")
                .build();

        assertThat(dto.getHttpStatus()).isEqualTo("500");
        assertThat(dto.getMessage()).isEqualTo("Internal Server Error");

        // Debe inicializar el timestamp automáticamente
        assertThat(dto.getTimestamp()).isNotNull();
    }

    @Test
    void testEqualsAndHashCode() {
        ErrorResponseDto dto1 = ErrorResponseDto.builder()
                .httpStatus("400")
                .message("Bad Request")
                .timestamp("11111")
                .build();

        ErrorResponseDto dto2 = ErrorResponseDto.builder()
                .httpStatus("400")
                .message("Bad Request")
                .timestamp("11111")
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void testToString() {
        ErrorResponseDto dto = ErrorResponseDto.builder()
                .httpStatus("401")
                .message("Unauthorized")
                .timestamp("22222")
                .build();

        String toString = dto.toString();

        assertThat(toString).contains("401");
        assertThat(toString).contains("Unauthorized");
        assertThat(toString).contains("22222");
    }
}
