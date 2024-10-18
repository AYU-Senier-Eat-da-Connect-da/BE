package com.eatda.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TOKEN_ALREADY_LOGGED_OUT(HttpStatus.BAD_REQUEST, "T-001", "잘못된 로그아웃 접근 방식입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
