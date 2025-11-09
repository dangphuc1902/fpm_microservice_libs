package com.fpm2025.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private int statusCode;
    private String message;
    private T data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .statusCode(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .statusCode(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> created(T data, String message) {
        return BaseResponse.<T>builder()
                .statusCode(201)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> error(int statusCode, String message) {
        return BaseResponse.<T>builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> badRequest(String message) {
        return BaseResponse.<T>builder()
                .statusCode(400)
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> notFound(String message) {
        return BaseResponse.<T>builder()
                .statusCode(404)
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> unauthorized(String message) {
        return BaseResponse.<T>builder()
                .statusCode(401)
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> forbidden(String message) {
        return BaseResponse.<T>builder()
                .statusCode(403)
                .message(message)
                .build();
    }
}
