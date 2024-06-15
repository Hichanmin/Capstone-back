package com.example.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "todo")


public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column(nullable = false, length = 30)
    private String todoEmail;

    @Column(nullable = false, length = 100)
    private String todoTitle;
    @Lob
    private String todoContent;

    @ColumnDefault("0")
    private int todoLike;

    @Column(nullable = false, length = 15)
    private String todoDate;

//    @Column(nullable = false, length = 15)
//    private String todoTime;

    @Column(nullable = false, length = 50)
    private String todoCategory;

    @Column(nullable = false, length = 15)
    private boolean todoCheck;

    @ColumnDefault("false")
    private boolean todoLikeCheck;

}