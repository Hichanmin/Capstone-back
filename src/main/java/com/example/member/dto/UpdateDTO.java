package com.example.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateDTO {
    private Long todoId;
    private String todoTitle;
    private String todoTime;
    private String todoContent;
    private String todoCategory;
    private boolean todoCheck;
}
