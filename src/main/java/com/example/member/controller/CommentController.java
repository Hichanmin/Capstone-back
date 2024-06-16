package com.example.member.controller;

import com.example.member.dto.CommentDTO;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import com.example.member.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData<String>> addComment(@RequestBody CommentDTO commentDTO, @RequestHeader("X-Member-Id") Long id) {
        try {
            ResponseData<String> responseData = commentService.addComment(commentDTO, id);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @PostMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData<String>> deleteComment(@RequestBody CommentDTO commentDTO) {
        try {
            ResponseData<String> responseData = commentService.deleteComment(commentDTO);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }
}
