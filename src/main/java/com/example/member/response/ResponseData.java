package com.example.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseData<T> {
    private int statusCode;
    private String success;
    private T data;

    public ResponseData(final int statusCode, final String success) {
        this.statusCode = statusCode;
        this.success = success;
        this.data = null;
    }

    public static <T> ResponseData<T> res(final int statusCode, final String success) {
        return res(statusCode, success, null);
    }

    public static <T> ResponseData<T> res(final int statusCode, final String success, final T data) {
        return ResponseData.<T>builder()
                .statusCode(statusCode)
                .success(success)
                .data(data)
                .build();
    }
}