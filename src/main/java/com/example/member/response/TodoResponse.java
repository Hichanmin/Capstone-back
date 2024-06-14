package com.example.member.response;

import com.example.member.dto.ResponseMyTodoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class TodoResponse {
    private List<ResponseMyTodoDTO> today;
    private List<ResponseMyTodoDTO> tomorrow;

    public TodoResponse(List<ResponseMyTodoDTO> today, List<ResponseMyTodoDTO> tomorrow) {
        this.today = today.isEmpty() ? null : today;
        this.tomorrow = tomorrow.isEmpty() ? null : tomorrow;
    }
}
