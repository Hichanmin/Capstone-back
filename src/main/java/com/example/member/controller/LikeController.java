package com.example.member.controller;

import com.example.member.dto.LikeDTO;
import com.example.member.entity.LikeEntity;
import com.example.member.response.ResponseData;
import com.example.member.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor

public class LikeController {

    private final LikeService likeService;

    @PostMapping(
            path = "/check",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseData<LikeEntity> likeCheck(@RequestBody LikeDTO likeDTO) {
        return likeService.likeCheck(likeDTO);
    }
}
