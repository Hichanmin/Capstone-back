package com.example.member.controller;


import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    org.slf4j.Logger logger = LoggerFactory.getLogger(MemberController.class);

    @PostMapping(
            path = "/save",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseData<MemberEntity> save(@RequestBody MemberDTO memberDTO) {
        return memberService.save(memberDTO);
    }

    @PostMapping(
            path = "/login",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseData<MemberDTO> login(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {
        ResponseData<MemberDTO> responseData = memberService.login(memberDTO);
        if (responseData.getStatusCode() == StatusCode.OK) {
            HttpSession session = request.getSession();
            session.setAttribute("loginEmail", responseData.getData().getMemberEmail());
        }
        return responseData;
    }

    @PostMapping(path = "/verify")
    public ResponseEntity<?> verify(@RequestBody MemberDTO memberDTO) {
        boolean success = memberService.verify(memberDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getMemberInfo(@PathVariable Long id) {
        try {
            ResponseData<MemberDTO> response = memberService.getMemberById(id);
            if (response.getData() != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseData.res(StatusCode.NOT_FOUND, "Member not found"));
            }
        } catch (Exception e) {
            logger.error("Error fetching member info: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, "Error fetching member info"));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        try {
            memberDTO.setId(id);  // DTO에 ID 설정
            ResponseData<MemberDTO> response = memberService.updateMemberCredentials(memberDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error updating member credentials: ", e);
            return ResponseEntity.badRequest().body(ResponseData.res(StatusCode.BAD_REQUEST, "Failed to update member credentials", e.getMessage()));
        }
    }


}




