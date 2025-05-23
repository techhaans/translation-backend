package com.domain.dto.response;

public class ApiResponseBuilder {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, "SUCCESS");
    }

    public static <T> ApiResponse<T> failure(T data, String message) {
        return new ApiResponse<>(data, message, "FAILURE");
    }
}
