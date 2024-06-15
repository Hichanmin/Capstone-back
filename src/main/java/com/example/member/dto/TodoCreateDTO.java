package com.example.member.dto;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoCreateDTO {
    private String todoTitle;
    private String todoContent;
    private String todoCategory;
    private String todoDate;
    private String todoTime;
    private boolean todoCheck;
}