package com.pwang.shopping.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    ENTITY_NOT_FOUND(400, "P001", " Entity Not Found"),

    INVALID_TYPE_VALUE(400, "P002", " Invalid Type Value"),

    UNAUTHORIZEDException (401, "P003", "Account verification failed"),

    NULLTokenException (401, "P004", "Token does not exist."),

    ExpiredJwtException(401, "P005", "AccessToKen expiration, RefreshToken reissuance"),

    ReLogin(401, "P006", "refreshToken expiration"),

    ForbiddenException(403, "P007", "You do not have permission."),

    DUPLICATION(409, "P008", "Account Duplication");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}