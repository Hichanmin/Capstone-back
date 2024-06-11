package com.example.member.response;

import com.example.member.dto.TodoDTO;

import java.util.List;

public class TodoResponse {
    private final List<TodoDTO> today;
    private final List<TodoDTO> tomorrow;

    public TodoResponse(List<TodoDTO> today, List<TodoDTO> tomorrow) {
        this.today = today;
        this.tomorrow = tomorrow;
    }
}