package com.example.member.controller;

import com.example.member.dto.LikeDTO;
import com.example.member.entity.LikeEntity;
import com.example.member.response.ResponseData;
import com.example.member.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor

public class LikeController {

    private final LikeService likeService;

    @PostMapping(
            path = "action")
    public ResponseData<LikeEntity> like(@RequestBody LikeDTO likeDTO, @RequestHeader("X-Member-Id") Long id ) {
        return likeService.like(likeDTO, id);
    }
}
