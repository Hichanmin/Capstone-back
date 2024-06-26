package com.example.member.dto;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoDTO {
    private Long todoId;
    private String todoTitle;
    private String todoContent;
    private String todoCategory;
    private int todoLike;
    private String todoDate;
    private boolean todoCheck;
    private String todoEmail;
    private boolean todoLikeCheck;
    private String todoTime;
}