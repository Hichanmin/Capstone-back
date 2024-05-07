package com.example.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "likes")

public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String likeEmail;

    @Column
    private String likeTodoId;

    @Column
    private boolean likeCheck;
}
