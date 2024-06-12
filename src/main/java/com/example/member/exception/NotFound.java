package com.example.member.exception;

public class NotFound extends RuntimeException {
    private final int statusCode;

    public NotFound(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
