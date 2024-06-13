package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.dto.TodoDTO;
import com.example.member.entity.TodoEntity;
import com.example.member.response.ResponseData;
import com.example.member.response.TodoResponse;
import com.example.member.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService; // TodoService 주입

    @PostMapping(
            path = "/todo/create",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseData<TodoEntity> save(@RequestBody TodoDTO todoDTO) {
        return todoService.save(todoDTO);
    }

    @GetMapping(
            path="/todo/list",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseData<TodoResponse> list(@RequestBody MemberDTO memberDTO) {
        return todoService.list(memberDTO);
    }

//    @PostMapping("/todo/delete/{todoDate}") // URL 경로에서 todoDate를 추출하여 삭제 요청 처리
//    public ResponseData<Void> delete(@PathVariable String todoDate, @RequestBody TodoDTO todoDTO) {
//        // 받은 값을 로그에 출력합니다.
//        System.out.println("Received todoDate: " + todoDate);
//        System.out.println("Received todoDTO: " + todoDTO);
//
//        // TodoService의 delete 메소드 호출
//        String todoEmail = todoDTO.getTodoEmail();
//        System.out.println("Received todoEmail: " + todoEmail);
//
//        return todoService.delete(todoDate, todoEmail);
//    }



//    @PostMapping("/todo/update/{todoDate}") // URL 경로에서 todoDate를 추출하여 업데이트 요청 처리
//    public ResponseData<TodoEntity> update(@PathVariable String todoDate, @RequestBody TodoDTO todoDTO) {
//        // 로그에 요청이 온 값을 출력합니다.
//        System.out.println("Received todoDate: " + todoDate);
//        System.out.println("Received todoDTO: " + todoDTO);
//
//
//        // todoEmail을 TodoDTO에서 추출합니다.
//        String todoEmail = todoDTO.getTodoEmail();
//        System.out.println("Received TodoEmail: " + todoEmail);
//        // 업데이트 서비스 호출
//        return todoService.update(todoDate, todoEmail, todoDTO);
//    }
//



    @RequestMapping(
            path = "/todo/alllist",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseData<List<TodoDTO>> getAllTodos() {
        return todoService.allList(true); // todoCheck 값이 1인 데이터만 가져오도록 요청
    }



}
