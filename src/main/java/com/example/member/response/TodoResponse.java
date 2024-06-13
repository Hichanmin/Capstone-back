package com.example.member.response;

import com.example.member.dto.ResponseMyTodoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class TodoResponse {
    private List<ResponseMyTodoDTO> today = null;
    private List<ResponseMyTodoDTO> tomorrow = null;

    public TodoResponse(List<ResponseMyTodoDTO> today, List<ResponseMyTodoDTO> tomorrow) {
        if (today.isEmpty()) {
            this.today = null;
            this.tomorrow = tomorrow;
        } else if (tomorrow.isEmpty()) {
            this.today = today;
            this.tomorrow = null;
        } else {
            this.today = today;
            this.tomorrow = tomorrow;
        }
    }
}
