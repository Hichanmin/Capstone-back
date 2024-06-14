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

    @PostMapping(
            path = "/todo/create",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResponseData<TodoEntity>> save(@RequestBody TodoCreateDTO todoCreateDTO) {
        try {
            ResponseData<TodoEntity> responseData = todoService.save(todoCreateDTO);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @GetMapping(
            path="/todo/list")
    public ResponseData<TodoResponse> list(@RequestHeader("Authorization") Long id) {
        return todoService.list(id);
    }

    @PostMapping("/todo/delete")
    public ResponseEntity<ResponseData<?>> delete(@RequestBody DeleteDTO deleteDTO) {
        try {
            ResponseData<?> responseData = todoService.delete(deleteDTO);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @PostMapping("/todo/update")
    public ResponseEntity<ResponseData<TodoEntity>> update(@RequestBody UpdateDTO updateDTO) {
        try {
            ResponseData<TodoEntity> responseData = todoService.update(updateDTO);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @PostMapping(
            path = "/todo/allList",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<ResponseData<List<TodoDTO>>> getAllTodos() {
        try {
            ResponseData<List<TodoDTO>> responseData = todoService.allList(true);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @PostMapping(
            path="/todo/searchTitle",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResponseData<List<TodoDTO>>> searchTitle(@RequestBody TodoDTO TodoDTO) {
        try {
            ResponseData<List<TodoDTO>> responseData = todoService.searchTitle(TodoDTO);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }

    @PostMapping("/todo/searchCategory")
    public ResponseEntity<ResponseData<List<TodoDTO>>> searchCategory(@RequestBody TodoDTO todoDTO) {
        try {
            ResponseData<List<TodoDTO>> responseData = todoService.searchCategory(todoDTO);
            return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE));
        }
    }
}

