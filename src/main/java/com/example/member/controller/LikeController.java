package com.example.member.controller;

import com.example.member.dto.LikeDTO;
import com.example.member.response.ResponseData;
import com.example.member.service.LikeService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(path = "/check")
    public ResponseEntity<?> likeCheck(@RequestBody LikeDTO likeDTO) {
        boolean success = likeService.likeCheck(likeDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        return ResponseEntity.ok().body(response);
    }
}
