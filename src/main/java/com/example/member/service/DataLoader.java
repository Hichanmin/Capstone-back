package com.example.member.service;

import com.example.member.entity.CommentEntity;
import com.example.member.entity.MemberEntity;
import com.example.member.entity.TodoEntity;
import com.example.member.repository.CommentRepository;
import com.example.member.repository.MemberRepository;
import com.example.member.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public DataLoader(MemberRepository memberRepository, TodoRepository todoRepository, CommentRepository commentRepository) {
        this.memberRepository = memberRepository;
        this.todoRepository = todoRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 더미 데이터 로드
        loadDummyData(50); // 50개의 더미 데이터 생성
    }

    private void loadDummyData(int count) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            // 회원 생성
            MemberEntity member = new MemberEntity();
            member.setMemberEmail("user" + i + "@example.com");
            member.setMemberPassword("password" + i);
            member.setMemberName("User " + i);

            memberRepository.save(member);

            // 투두 생성
            TodoEntity todo = new TodoEntity();
            todo.setTodoEmail(member.getMemberEmail());
            todo.setTodoTitle("Todo Title " + i);
            todo.setTodoContent("Content for todo " + i);
            todo.setTodoLike(random.nextInt(100)); // 0부터 99까지의 랜덤한 좋아요 수
            todo.setTodoDate("2024-06-15");
            todo.setTodoCategory("#Category1 #Category2 #Category3");
            todo.setTodoCheck(random.nextBoolean()); // 랜덤한 체크 여부
            todo.setTodoTime("오후 3시 6분");
            todoRepository.save(todo);

            // 댓글 생성
            CommentEntity comment = new CommentEntity();
            comment.setComment("Comment for todo " + i + " by user " + i);
            comment.setCommentMemberEmail(member.getMemberEmail());
            comment.setCommentTodoId(todo.getId());

            commentRepository.save(comment);
        }
    }
}
