package com.example.member.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
}