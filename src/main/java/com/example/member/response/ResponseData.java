package com.example.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor // 생성자를 자동으로 만들어 준다
@Builder
public class ResponseData<T> {
    private int statusCode;
    private String success;
    private T data;
    private int a;
    public ResponseData(final int statusCode, final String success) {
        this.statusCode = statusCode;
        this.success = success;
        this.data = null;
    }

    public static<T> ResponseData<T> res(final int statusCode, final String success) {
        return res(statusCode, success, null);
    }

    public static<T> ResponseData<T> res(final int statusCode, final String success, final T t) {
        return ResponseData.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .success(success)
                .build();
    }
}