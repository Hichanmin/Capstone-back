package com.example.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.member.dto.CommentDTO;
import com.example.member.entity.CommentEntity;
import com.example.member.entity.TodoEntity;
import com.example.member.repository.CommentRepository;
import com.example.member.repository.TodoRepository;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, TodoRepository todoRepository) {
        this.commentRepository = commentRepository;
        this.todoRepository = todoRepository;
    }

    public ResponseData<String> addComment(CommentDTO commentDTO) {
        // CommentDTO에서 필요한 정보 추출
        String commentText = commentDTO.getComment();
        String memberEmail = commentDTO.getCommentMemberEmail(); // String 타입으로 유지
        Long todoId = commentDTO.getCommentTodoId();

        // 요청한 투두 아이디가 존재하는지 확인
        Optional<TodoEntity> optionalTodo = todoRepository.findById(todoId);
        if (optionalTodo.isPresent()) {
            // 투두가 존재하면 댓글 추가
            CommentEntity newComment = new CommentEntity();
            newComment.setCommentTodoId(todoId);
            newComment.setComment(commentText);
            newComment.setCommentMemberEmail(memberEmail); // String 타입으로 저장
            commentRepository.save(newComment);

            return ResponseData.res(StatusCode.OK, Success.TRUE, "Comment added successfully");
        } else {
            // 투두가 존재하지 않으면 적절한 응답 반환
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, "Todo not found");
        }

    }

    public ResponseData<String> deleteComment(CommentDTO commentDTO) {
        try {
            System.out.println("Received commentId from client: " + commentDTO); // 클라이언트에서 받은 commentId 출력
            Optional<CommentEntity> commentEntityOptional = commentRepository.findById(commentDTO.getCommentId());
            if (commentEntityOptional.isEmpty()) {
                return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, "Comment not found");
            }
            CommentEntity commentEntity = commentEntityOptional.get();
            commentRepository.delete(commentEntity);
            // 변경 사항을 즉시 데이터베이스에 반영하기 위해 flush() 호출
            commentRepository.flush();

            return ResponseData.res(StatusCode.OK, Success.TRUE, "Comment deleted successfully");
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, "An error occurred while deleting the comment");
        }
    }


}

