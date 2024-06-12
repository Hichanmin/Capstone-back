package com.example.member.controller;

import com.example.member.dto.DeleteDTO;
import com.example.member.dto.TodoDTO;
import com.example.member.entity.TodoEntity;
import com.example.member.response.ResponseData;
import com.example.member.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return todoService.save(todoDTO); // TodoService의 인스턴스 메서드 호출
    }

    @PostMapping(
            path="/todo/mylist",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseData<List<TodoDTO>> showmylist(@RequestBody TodoDTO todoDTO) {
        // 서비스의 메서드를 호출하여 결과 반환
        return todoService.showlist(todoDTO);
    }

    @PostMapping("/todo/delete")
    public ResponseData<?> delete(@RequestBody DeleteDTO deleteDTO) {
        return todoService.delete(deleteDTO);
    }

    @PostMapping("/todo/update/{todoId}") // URL 경로에서 todoId를 추출하여 업데이트 요청 처리
    public ResponseData<TodoEntity> update(@PathVariable Long todoId, @RequestBody TodoDTO todoDTO) {
        // 로그에 요청이 온 값을 출력합니다.
        System.out.println("Received todoId: " + todoId);
        System.out.println("Received todoDTO: " + todoDTO);

        // 업데이트 서비스 호출
        return todoService.update(todoId, todoDTO);
    }

    @RequestMapping(
            path = "/todo/alllist",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseData<List<TodoDTO>> getAllTodos() {
        return todoService.allList(true); // todoCheck 값이 1인 데이터만 가져오도록 요청
    }

    @PostMapping(
            path="/todo/searchTitle",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseData<List<TodoDTO>> searchTitle(@RequestBody TodoDTO TodoDTO) {
        return todoService.searchTitle(TodoDTO);
    }

    @PostMapping(
            path="/todo/searchCategory",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseData<List<TodoDTO>> searchCategory(@RequestBody TodoDTO TodoDTO) {
        return todoService.searchCategory(TodoDTO);
    }



}
