package com.eatda.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final boolean success;
    private final ErrorDetail error;

    @Getter
    @Builder
    public static class ErrorDetail {
        private final String code;
        private final int status;
        private final String message;
        private final LocalDateTime timestamp;
        private final String path;
    }

    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return ErrorResponse.builder()
                .success(false)
                .error(ErrorDetail.builder()
                        .code(errorCode.getErrorCode())
                        .status(errorCode.getHttpStatus().value())
                        .message(errorCode.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(path)
                        .build())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return ErrorResponse.builder()
                .success(false)
                .error(ErrorDetail.builder()
                        .code(errorCode.getErrorCode())
                        .status(errorCode.getHttpStatus().value())
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .path(path)
                        .build())
                .build();
    }

    public static ErrorResponse of(int status, String code, String message, String path) {
        return ErrorResponse.builder()
                .success(false)
                .error(ErrorDetail.builder()
                        .code(code)
                        .status(status)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .path(path)
                        .build())
                .build();
    }
}
