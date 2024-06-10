package com.example.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LikeDTO {
    private Long likeId;
    private String likeEmail;
    private String likeTodoId;
}
// like check 구현중