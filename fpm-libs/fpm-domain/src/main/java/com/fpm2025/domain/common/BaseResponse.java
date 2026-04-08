package com.fpm2025.domain.common;
 
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> implements Serializable {
    
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    
    @Builder.Default
    private int statusCode = 200;
    
    private String message;
    private T data;
    private String error;
    private String path;
    private Map<String, String> validationErrors;
 
    public BaseResponse(int statusCode, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
 
    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .statusCode(200)
                .message(message)
                .data(data)
                .build();
    }
 
    public static <T> BaseResponse<T> error(int statusCode, String error, String message, String path) {
        return BaseResponse.<T>builder()
                .statusCode(statusCode)
                .error(error)
                .message(message)
                .path(path)
                .build();
    }
 
    /**
     * Backward compatible error method
     */
    public static <T> BaseResponse<T> error(T data, String message) {
        return BaseResponse.<T>builder()
                .statusCode(400)
                .message(message)
                .data(data)
                .build();
    }
}
