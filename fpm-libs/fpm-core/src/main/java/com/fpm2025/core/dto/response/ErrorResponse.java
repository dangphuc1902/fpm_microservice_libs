package com.fpm2025.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private String path;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private Map<String, String> validationErrors;
    private String debugMessage;

    public static ErrorResponse of(int status, String error, String message) {
        return ErrorResponse.builder()
                .status(status)
                .error(error)
                .message(message)
                .build();
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return ErrorResponse.builder()
                .status(400)
                .error("VALIDATION_ERROR")
                .message("Validation failed")
                .validationErrors(errors)
                .build();
    }
}
