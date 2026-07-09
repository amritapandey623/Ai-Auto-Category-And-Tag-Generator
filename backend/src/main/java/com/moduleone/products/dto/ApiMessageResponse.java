package com.moduleone.products.dto;

public class ApiMessageResponse {
    private String message;

    public ApiMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
