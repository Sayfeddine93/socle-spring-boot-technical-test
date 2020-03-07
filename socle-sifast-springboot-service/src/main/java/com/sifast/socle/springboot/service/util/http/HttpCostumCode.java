package com.sifast.socle.springboot.service.util.http;

public enum HttpCostumCode {
    NOT_FOUND(44), BAD_REQUEST(4), SERVER_ERROR(5), TOKEN_EXPIRED(43);

    private int code;

    HttpCostumCode(int message) {
        this.code = message;
    }

    public int getValue() {
        return code;
    }
}
