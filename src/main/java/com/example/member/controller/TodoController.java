package com.example.member.controller;

import com.example.member.dto.*;
import com.example.member.entity.TodoEntity;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import com.example.member.response.TodoResponse;
import com.example.member.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo/create")
    public ResponseEntity<ResponseData<TodoEntity>> save(@RequestBody TodoCreateDTO todoCreateDTO, @RequestHeader("X-Member-Id") Long memberId) {
        try {
            ResponseData<TodoEntity> responseData = todoService.save(todoCreateDTO, memberId);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }



    @GetMapping(
            path="/todo/list")
    public ResponseData<TodoResponse> list(@RequestHeader("X-Member-Id") Long id) {
        return todoService.list(id);
    }

    @PostMapping("/todo/delete")
    public ResponseEntity<ResponseData<?>> delete(@RequestBody DeleteDTO deleteDTO, @RequestHeader("X-Member-Id") Long id) {
        try {
            ResponseData<?> responseData = todoService.delete(deleteDTO, id);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @PostMapping("/todo/update")
    public ResponseEntity<ResponseData<TodoEntity>> update(@RequestBody UpdateDTO updateDTO, @RequestHeader("X-Member-Id") Long id) {
        try {
            ResponseData<TodoEntity> responseData = todoService.update(updateDTO,id);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @GetMapping(path = "/todo/allList")
    public ResponseEntity<ResponseData<List<AllTodoDTO>>> getAllTodos(@RequestHeader("X-Member-Id") Long id) {
        try {
            ResponseData<List<AllTodoDTO>> responseData = todoService.allList(id);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @GetMapping("/todo/searchTitle")
    public ResponseEntity<ResponseData<List<AllTodoDTO>>> searchTitle(@RequestParam String todoTitle, @RequestHeader("X-Member-Id") Long id) {
        try {
            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setTodoTitle(todoTitle);

            ResponseData<List<AllTodoDTO>> responseData = todoService.searchTitle(todoDTO, id);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }


    @GetMapping("/todo/searchCategory")
    public ResponseEntity<ResponseData<List<AllTodoDTO>>> searchCategory(@RequestParam String todoCategory ,@RequestHeader("X-Member-Id") Long id) {
        try {
            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setTodoCategory(todoCategory);

            ResponseData<List<AllTodoDTO>> responseData = todoService.searchCategory(todoDTO, id);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

}

