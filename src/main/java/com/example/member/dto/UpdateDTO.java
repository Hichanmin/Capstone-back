package com.example.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateDTO {
    private Long memberId;
    private Long todoId;
    private String todoTitle;
    private String todoContent;
    private String todoCategory;
    private int todoLikes;
    private boolean todoCheck;
}
