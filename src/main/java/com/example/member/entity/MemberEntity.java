package com.example.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "member") //데이터베이스 user 테이블 생성

public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, length = 100, unique = true)
    private String memberEmail;

    @Column(nullable = false, length = 20)
    private String memberPassword;

    @Column(nullable = false, length = 30)
    private String memberName;
}