package co.com.crediya.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDto implements Serializable {
    private String httpStatus;
    private String message;

    @Builder.Default
    private String timestamp = String.valueOf(System.currentTimeMillis());
}
