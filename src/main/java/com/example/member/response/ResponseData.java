package com.example.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor // 모든 필드에 대한 생성자를 자동으로 생성
@Builder
public class ResponseData<T> {
    private int statusCode;
    private String success;
    private T data;
    private T today;
    private T tomorrow;

    // 두 필드만을 초기화하는 생성자
    public ResponseData(final int statusCode, final String success) {
        this.statusCode = statusCode;
        this.success = success;
        this.data = null;
        this.today = null;
        this.tomorrow = null;
    }

    // 정적 메서드로 ResponseData 객체 생성
    public static <T> ResponseData<T> res(final int statusCode, final String success) {
        return res(statusCode, success, null);
    }

    // 정적 메서드로 ResponseData 객체 생성 (today와 tomorrow 포함)
    public static <T> ResponseData<T> res(final int statusCode, final String success, final T data) {
        return ResponseData.<T>builder()
                .statusCode(statusCode)
                .success(success)
                .data(data)
                .build();
    }

    public static <T> ResponseData<T> listres(final int statusCode, final String success, final T today, final T tomorrow) {
        return ResponseData.<T>builder()
                .statusCode(statusCode)
                .success(success)
                .today(today)
                .tomorrow(tomorrow)
                .build();
    }
}
