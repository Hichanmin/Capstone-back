package com.example.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LikeDTO {
    private Long likeId;
    private Long likeMemberId;
    private Long likeTodoId;
}
