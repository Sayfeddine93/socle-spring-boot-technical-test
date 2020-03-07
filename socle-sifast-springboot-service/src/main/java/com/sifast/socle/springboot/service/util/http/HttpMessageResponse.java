package com.sifast.socle.springboot.service.util.http;

public class HttpMessageResponse {

    private String message;

    public HttpMessageResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
