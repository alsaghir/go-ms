package com.goms.interfaces.exception.dto;

public class ApiSubError {

    private String code;
    private String message;

    public ApiSubError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public ApiSubError setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiSubError setMessage(String message) {
        this.message = message;
        return this;
    }
}
