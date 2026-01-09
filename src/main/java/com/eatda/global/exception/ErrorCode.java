package com.eatda.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Auth
    TOKEN_ALREADY_LOGGED_OUT(HttpStatus.BAD_REQUEST, "A-001", "잘못된 로그아웃 접근 방식입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A-002", "인증이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-003", "유효하지 않은 토큰입니다."),

    // User - Child
    CHILD_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "해당 아동을 찾을 수 없습니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "U-002", "잔액이 부족합니다."),

    // User - Sponsor
    SPONSOR_NOT_FOUND(HttpStatus.NOT_FOUND, "U-003", "해당 후원자를 찾을 수 없습니다."),

    // User - President
    PRESIDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "U-004", "해당 사장님을 찾을 수 없습니다."),

    // Restaurant
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "R-001", "해당 식당을 찾을 수 없습니다."),

    // Menu
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "M-001", "해당 메뉴를 찾을 수 없습니다."),

    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O-001", "해당 주문을 찾을 수 없습니다."),
    ORDER_CREATION_FAILED(HttpStatus.BAD_REQUEST, "O-002", "주문을 생성할 수 없습니다."),

    // Review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "V-001", "해당 리뷰를 찾을 수 없습니다."),

    // Common
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "C-001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C-002", "서버 내부 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}

