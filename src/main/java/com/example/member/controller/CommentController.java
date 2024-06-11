package com.example.member.controller;

import com.example.member.dto.CommentDTO;
import com.example.member.response.ResponseData;
import com.example.member.entity.CommentEntity;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import com.example.member.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<String> addComment(@RequestBody CommentDTO commentDTO) {
        return commentService.addComment(commentDTO);
    }
}

