package com.example.member.service;

import com.example.member.dto.DeleteDTO;
import com.example.member.dto.MemberDTO;
import com.example.member.dto.TodoDTO;
import com.example.member.dto.UpdateDTO;
import com.example.member.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
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

    private final MemberRepository memberRepository;
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
    public ResponseData<List<TodoDTO>> searchTitle(TodoDTO todoDTO) {
        String Categorykeyword = todoDTO.getTodoTitle(); // 키워드 추출
        Optional<List<TodoEntity>> todoEntities = todoRepository.findByTodoTitleContaining(Categorykeyword);

        if (todoEntities.isPresent() && !todoEntities.get().isEmpty()) {
            List<TodoDTO> todoDTOList = todoEntities.get().stream()
                    .filter(TodoEntity::isTodoCheck) // todoCheck가 true인 엔티티만 필터링
                    .map(TodoMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList());
            return ResponseData.res(StatusCode.OK, Success.TRUE, todoDTOList);
        } else {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, null);
        }
    }


    public ResponseData<List<TodoDTO>> searchCategory(TodoDTO todoDTO) {
        String Categorykeyword = todoDTO.getTodoCategory(); // 키워드 추출
        Optional<List<TodoEntity>> todoEntities = todoRepository.findByTodoCategoryContaining(Categorykeyword);

        if (todoEntities.isPresent() && !todoEntities.get().isEmpty()) {
            List<TodoDTO> todoDTOList = todoEntities.get().stream()
                    .filter(TodoEntity::isTodoCheck) // todoCheck가 true인 엔티티만 필터링
                    .map(TodoMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList());
            return ResponseData.res(StatusCode.OK, Success.TRUE, todoDTOList);
        } else {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, null);
        }
    }


    public ResponseData<?> delete(DeleteDTO deleteDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(deleteDTO.getMemberId());
        if (optionalMemberEntity.isPresent()) {
            String memberEmail = optionalMemberEntity.get().getMemberEmail();
            Optional<TodoEntity> optionalTodoEntity = todoRepository.findByIdAndTodoEmail(deleteDTO.getTodoId(), memberEmail);
            if (optionalTodoEntity.isPresent()) {
                optionalTodoEntity.ifPresent(todoRepository::delete);
                return ResponseData.res(StatusCode.OK, Success.TRUE);
            } else {
                return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
            }
        }
        return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
    }


    public ResponseData<TodoEntity> update(UpdateDTO updateDTO) {
        try {
            TodoEntity todoEntity = Mapper.INSTANCE.updateDTO;
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(updateDTO.getMemberId());
            if (optionalMemberEntity.isPresent()) {
                String memberEmail = optionalMemberEntity.get().getMemberEmail();
                Optional<TodoEntity> optionalTodoEntity = todoRepository.findByIdAndTodoEmail(updateDTO.getTodoId(), memberEmail);
                if(optionalTodoEntity.isPresent()) {

                }
                todoEntity.setTodoTitle(todoDTO.getTodoTitle());
                todoEntity.setTodoContent(todoDTO.getTodoContent());
                todoEntity.setTodoCategory(todoDTO.getTodoCategory());
                todoEntity.setTodoLikes(todoDTO.getTodoLikes());
                todoEntity.setTodoCheck(todoDTO.isTodoCheck());

                todoRepository.save(todoEntity);

                TodoEntity updatedTodo = todoRepository.save(todoEntity);
                return ResponseData.res(StatusCode.OK, Success.TRUE, updatedTodo);
            } else {
                return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
            }
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }




}