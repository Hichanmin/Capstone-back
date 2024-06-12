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
        loadMemberData();
        loadTodoData();
        loadCommentData();
    }

    private void loadMemberData() {
        MemberEntity member1 = new MemberEntity();
        member1.setMemberEmail("user1@example.com");
        member1.setMemberPassword("password1");
        member1.setMemberName("User One");

        MemberEntity member2 = new MemberEntity();
        member2.setMemberEmail("user2@example.com");
        member2.setMemberPassword("password2");
        member2.setMemberName("User Two");

        MemberEntity member3 = new MemberEntity();
        member3.setMemberEmail("user3@example.com");
        member3.setMemberPassword("password3");
        member3.setMemberName("User Three");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

    private void loadTodoData() {
        TodoEntity todo1 = new TodoEntity();
        todo1.setTodoEmail("user1@example.com");
        todo1.setTodoTitle("Todo Title 1");
        todo1.setTodoContent("Content for todo 1");
        todo1.setTodoLike(0);
        todo1.setTodoDate("2024-01-01");
        todo1.setTodoCategory("#일, #운동, #힘들다");
        todo1.setTodoCheck(true);
        todo1.setTodoLikes(0);

        TodoEntity todo2 = new TodoEntity();
        todo2.setTodoEmail("user2@example.com");
        todo2.setTodoTitle("Todo Title 2");
        todo2.setTodoContent("Content for todo 2");
        todo2.setTodoLike(0);
        todo2.setTodoDate("2024-01-02");
        todo2.setTodoCategory("#게임 #롤 #피파");
        todo2.setTodoCheck(false);
        todo2.setTodoLikes(0);

        TodoEntity todo3 = new TodoEntity();
        todo3.setTodoEmail("user3@example.com");
        todo3.setTodoTitle("Todo Title 3");
        todo3.setTodoContent("Content for todo 3");
        todo3.setTodoLike(0);
        todo3.setTodoDate("2024-01-03");
        todo3.setTodoCategory("#쇼핑 #옷구경 #나들이");
        todo3.setTodoCheck(true);
        todo3.setTodoLikes(0);

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
    }

    private void loadCommentData() {
        CommentEntity comment1 = new CommentEntity();
        comment1.setComment("Comment for todo 1 by user1");
        comment1.setCommentTodoId(1L);
        comment1.setCommentMemberEmail("user1@example.com");

        CommentEntity comment2 = new CommentEntity();
        comment2.setComment("Comment for todo 2 by user2");
        comment2.setCommentTodoId(2L);
        comment2.setCommentMemberEmail("user2@example.com");

        CommentEntity comment3 = new CommentEntity();
        comment3.setComment("Comment for todo 3 by user3");
        comment3.setCommentTodoId(3L);
        comment3.setCommentMemberEmail("user3@example.com");

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
    }
}
