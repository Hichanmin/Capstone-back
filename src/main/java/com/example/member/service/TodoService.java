package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.dto.TodoDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.entity.TodoEntity;
import com.example.member.mapper.MemberMapper;
import com.example.member.mapper.TodoMapper;
import com.example.member.repository.TodoRepository;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public ResponseData<TodoEntity> save(TodoDTO todoDTO) {
        try {
            // TodoDTO에서 받은 값을 TodoEntity에 설정
            TodoEntity todoEntity = TodoMapper.INSTANCE.toEntity(todoDTO);
            todoEntity = todoRepository.save(todoEntity);
            // 저장된 TodoEntity를 ResponseData에 담아 반환
            return ResponseData.res(StatusCode.OK, Success.TRUE);
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }

    public ResponseData<List<TodoDTO>> showlist(TodoDTO todoDTO) {
        Optional<List<TodoEntity>> byTodoEmail = todoRepository.findByTodoEmail(todoDTO.getTodoEmail());
        if (byTodoEmail.isPresent()) {
            List<TodoEntity> todoList = byTodoEmail.get();
            List<TodoDTO> todoDTOList = new ArrayList<>();
            for (TodoEntity todoEntity : todoList) {
                TodoDTO mappedTodoDTO = TodoMapper.INSTANCE.toDTO(todoEntity);
                todoDTOList.add(mappedTodoDTO);
            }
            return ResponseData.res(StatusCode.OK, Success.TRUE, todoDTOList);
        } else {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, null);
        }
    }

    public ResponseData<List<TodoDTO>> allList(boolean todoCheck) { // 자료형을 boolean으로 변경
        if (todoCheck) { // boolean 값에 따라 수정
            List<TodoEntity> allTodos = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

            // todoCheck 값이 true인 TodoEntity만 필터링
            List<TodoEntity> filteredTodos = allTodos.stream()
                    .filter(todoEntity -> todoEntity.isTodoCheck()) // boolean 값 확인
                    .collect(Collectors.toList());

            List<TodoDTO> todoDTOList = filteredTodos.stream()
                    .map(todoEntity -> TodoMapper.INSTANCE.toDTO(todoEntity))
                    .collect(Collectors.toList());

            return ResponseData.res(StatusCode.OK, Success.TRUE, todoDTOList);
        } else {
            // todoCheck가 false인 경우에는 빈 리스트 반환
            return ResponseData.res(StatusCode.OK, Success.TRUE, new ArrayList<>());
        }
    }

    // 나머지 메서드는 동일하게 유지


    public ResponseData<TodoEntity> likeTodo(Long id) {
        Optional<TodoEntity> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            TodoEntity todo = optionalTodo.get();
            // 좋아요 수가 null인 경우만 초기화하고, 그렇지 않으면 현재 좋아요 수를 사용
            int currentLikes = todo.getTodoLikes();
            todo.setTodoLikes(currentLikes + 1); // 좋아요 수 증가
            TodoEntity updatedTodo = todoRepository.save(todo);
            return ResponseData.res(StatusCode.OK, Success.TRUE, updatedTodo);
        } else {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }

    public ResponseData<Void> delete(String todoDate, String todoEmail) {
        try {
            // todoRepository에서 해당하는 todoEntity를 찾아 삭제
            TodoEntity todoEntity = todoRepository.findByTodoDateAndTodoEmail(todoDate, todoEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Todo 항목을 찾을 수 없습니다. Date: " + todoDate + ", Email: " + todoEmail));
            todoRepository.delete(todoEntity);
            return ResponseData.res(StatusCode.OK, Success.TRUE);
        } catch (IllegalArgumentException e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }




    public ResponseData<TodoEntity> update(String todoDate, String todoEmail, TodoDTO todoDTO) {
        try {
            // todoRepository에서 해당하는 todoEntity를 찾아 업데이트
            TodoEntity todoEntity = todoRepository.findByTodoDateAndTodoEmail(todoDate, todoEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Todo 항목을 찾을 수 없습니다. Date: " + todoDate + ", Email: " + todoEmail));

            todoEntity.setTodoTitle(todoDTO.getTodoTitle());
            todoEntity.setTodoContent(todoDTO.getTodoContent());
            todoEntity.setTodoCategory(todoDTO.getTodoCategory());
            todoEntity.setTodoLikes(todoDTO.getTodoLikes());
            todoEntity.setTodoCheck(todoDTO.isTodoCheck());

            TodoEntity updatedTodo = todoRepository.save(todoEntity);
            return ResponseData.res(StatusCode.OK, Success.TRUE, updatedTodo);
        } catch (IllegalArgumentException e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }


}