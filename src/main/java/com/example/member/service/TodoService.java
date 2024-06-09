package com.example.member.service;

import com.example.member.dto.DeleteDTO;
import com.example.member.dto.TodoDTO;
import com.example.member.dto.UpdateDTO;
import com.example.member.exception.BadRequest;
import com.example.member.exception.NotFound;
import com.example.member.mapper.UpdateMapper;
import com.example.member.repository.MemberRepository;
import com.example.member.entity.MemberEntity;
import com.example.member.entity.TodoEntity;
import com.example.member.mapper.TodoMapper;
import com.example.member.repository.MemberRepository;
import com.example.member.repository.TodoRepository;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ResponseStatus
public class TodoService {

    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

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

    public ResponseData<List<TodoDTO>> list(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberDTO.getId());
        if (memberEntity.isPresent()) {
            String memberEmail = memberEntity.get().getMemberEmail();
            Optional<List<TodoEntity>> optionalTodoEntityList = todoRepository.findByTodoEmail(memberEmail);

            if (optionalTodoEntityList.isPresent()) {
                List<TodoEntity> todoEntityList = optionalTodoEntityList.get();
                List<TodoDTO> todoDTOList = new ArrayList<>();
                for(TodoEntity todoEntity : todoEntityList) {
                    TodoDTO mappedTodoDTO = TodoMapper.INSTANCE.toDTO(todoEntity);
                    todoDTOList.add(mappedTodoDTO);
                }
                return ResponseData.res(StatusCode.OK, Success.TRUE, todoDTOList);
            } else {
                return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, null);
            }
        } else {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, null);
        }
    }

    public ResponseData<List<TodoDTO>> allList(boolean todoCheck) {
        if (todoCheck) {
            List<TodoEntity> allTodos = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

            List<TodoEntity> filteredTodos = allTodos.stream()
                    .filter(TodoEntity::isTodoCheck)
                    .toList();

            List<TodoDTO> todoDTOList = filteredTodos.stream()
                    .map(TodoMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList());

            return ResponseData.res(StatusCode.OK, Success.TRUE, todoDTOList);
        } else {
            return ResponseData.res(StatusCode.OK, Success.TRUE, new ArrayList<>());
        }
    }

    public ResponseData<List<TodoDTO>> searchTitle(TodoDTO todoDTO) {
        try {
            String titleKeyword = todoDTO.getTodoTitle(); // 키워드 추출
            Optional<List<TodoEntity>> todoEntities = todoRepository.findByTodoTitleContaining(titleKeyword);

            if (todoEntities.isPresent() && !todoEntities.get().isEmpty()) {
                List<TodoDTO> todoDTOList = todoEntities.get().stream()
                        .filter(TodoEntity::isTodoCheck) // todoCheck가 true인 엔티티만 필터링
                        .map(TodoMapper.INSTANCE::toDTO)
                        .collect(Collectors.toList());
                return ResponseData.res(StatusCode.OK, Success.TRUE, todoDTOList);
            } else {
                return ResponseData.res(StatusCode.NOT_FOUND, Success.FALSE);
            }
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE);
        }
    }



    public ResponseData<List<TodoDTO>> searchCategory(TodoDTO todoDTO) {
        try {
            String categoryKeyword = todoDTO.getTodoCategory();
            Optional<List<TodoEntity>> todoEntities = todoRepository.findByTodoCategoryContaining(categoryKeyword);

            if (todoEntities.isPresent()) {
                List<TodoDTO> todoDTOList = todoEntities.get().stream()
                        .filter(TodoEntity::isTodoCheck)
                        .map(TodoMapper.INSTANCE::toDTO)
                        .collect(Collectors.toList());

                if (!todoDTOList.isEmpty()) {
                    return ResponseData.res(StatusCode.OK, Success.TRUE, todoDTOList);
                } else {
                    return ResponseData.res(StatusCode.NOT_FOUND, Success.FALSE);
                }
            } else {
                return ResponseData.res(StatusCode.NOT_FOUND, Success.FALSE);
            }
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
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
            // UpdateDTO를 TodoEntity로 변환
            TodoEntity todoEntity = UpdateMapper.INSTANCE.toEntity(updateDTO);

            // MemberEntity를 조회
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(updateDTO.getMemberId());
            System.out.println("멤버엔티티 : " + optionalMemberEntity);
            if (optionalMemberEntity.isPresent()) {
                String memberEmail = optionalMemberEntity.get().getMemberEmail();
                System.out.println("멤버이메일" + memberEmail);

                Optional<TodoEntity> optionalTodoEntity = todoRepository.findByIdAndTodoEmail(updateDTO.getTodoId(), memberEmail);
                if (optionalTodoEntity.isPresent()) {
                    System.out.println("투두엔티티" + optionalTodoEntity);
                    TodoEntity existingTodo = optionalTodoEntity.get();
                    System.out.println("익사이팅 투두" + existingTodo );
                    // 기존 TodoEntity에 업데이트할 내용을 설정
                    existingTodo.setTodoTitle(todoEntity.getTodoTitle());
                    existingTodo.setTodoContent(todoEntity.getTodoContent());
                    existingTodo.setTodoCategory(todoEntity.getTodoCategory());
                    existingTodo.setTodoCheck(todoEntity.isTodoCheck());

                    // 업데이트된 TodoEntity를 저장
                    TodoEntity updatedTodo = todoRepository.save(existingTodo);
                    return ResponseData.res(StatusCode.OK, Success.TRUE, updatedTodo);
                } else {
                    throw new NotFound("Todo not found for the given ID and member email", StatusCode.NOT_FOUND);
                }
            } else {
                throw new BadRequest("Member not found for the given ID", StatusCode.BAD_REQUEST);
            }
        } catch (NotFound e) {
            // NotFound 예외 처리
            return ResponseData.res(e.getStatusCode(), Success.FALSE);
        } catch (BadRequest e) {
            // BadRequest 예외 처리
            return ResponseData.res(e.getStatusCode(), Success.FALSE);
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseData.res(StatusCode.INTERNAL_SERVER_ERROR, Success.FALSE);
        }
    }







}