package com.example.member.exception;

public class BadRequest extends RuntimeException {
    private final int statusCode;

    public BadRequest(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
