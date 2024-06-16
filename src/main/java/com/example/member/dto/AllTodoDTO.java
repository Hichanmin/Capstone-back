package com.example.member.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AllTodoDTO {
    private Long id;
    private String todoTitle;
    private String todoContent;
    private String todoCategory;
    private int todoLike;
    private String todoEmail;
    private String todoDate;
    private boolean todoCheck;
    private boolean todoLikeCheck;
    private String todoTime;
    private List<CommentDTO> comment;
}
