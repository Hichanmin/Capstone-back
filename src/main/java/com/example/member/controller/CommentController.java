package com.example.member.controller;

import com.example.member.dto.CommentDTO;
import com.example.member.response.ResponseData;
import com.example.member.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<String> addComment(@RequestBody CommentDTO commentDTO) {
        System.out.println("add request : " + commentDTO);
        return commentService.addComment(commentDTO);
    }

    @PostMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<String> deleteComment(@RequestBody CommentDTO commentDTO) {
        System.out.println("받은 Request "  + commentDTO);
        return commentService.deleteComment(commentDTO);
    }
}
