package com.example.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LikeDTO {
    private Long id;
    private String likeEmail;
    private String likeTodoId;
    private boolean likeCheck;
}
