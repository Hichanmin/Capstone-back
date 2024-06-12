package com.example.member.dto;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoDTO {
    private Long id;
    private String todoTitle;
    private String todoContent;
    private String todoCategory;
    private int todoLike;
    private String todoDate;
    private boolean todoCheck;
    private String todoEmail;
}