package com.example.member.service;

import java.time.LocalDate;

import com.example.member.dto.*;
import com.example.member.entity.CommentEntity;
import com.example.member.entity.LikeEntity;
import com.example.member.exception.BadRequest;
import com.example.member.exception.NotFound;
import com.example.member.mapper.CommentMapper;
import com.example.member.mapper.UpdateMapper;
import com.example.member.repository.CommentRepository;
import com.example.member.repository.LikeRepository;
import com.example.member.repository.MemberRepository;
import com.example.member.entity.MemberEntity;
import com.example.member.entity.TodoEntity;
import com.example.member.mapper.TodoMapper;
import com.example.member.repository.TodoRepository;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import com.example.member.response.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.format.DateTimeFormatter;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ResponseStatus
public class TodoService {

    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    // 오늘 날짜와 내일 날짜 반환 yyyy-MM-dd format 으로 반환
    public DateDTO getDate() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String todayStr = today.format(formatter);
            String tomorrowStr = tomorrow.format(formatter);
            System.out.println("getDate today: " + todayStr + ", tomorrow :" + tomorrowStr + "Success");
            return new DateDTO(todayStr, tomorrowStr);
        } catch (Exception e) {
            System.out.println("getDate error"); //getDate 함수 에러
            return null;
        }
    }

    public ResponseData<TodoEntity> save(TodoCreateDTO todoCreateDTO, Long memberId) {
        try {
            Optional<MemberEntity> memberEntity = memberRepository.findById(memberId);
            if (memberEntity.isPresent()) {
                TodoEntity todoEntity = TodoMapper.INSTANCE.totoEntity(todoCreateDTO);
                todoEntity.setTodoEmail(memberEntity.get().getMemberEmail());
                todoRepository.save(todoEntity);
                return ResponseData.res(StatusCode.OK, Success.TRUE);
            }
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }
    public List<CommentDTO> comments(Long id) {
        Optional<List<CommentEntity>> optionalCommentEntityList = commentRepository.findByCommentTodoId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if (optionalCommentEntityList.isPresent()) {
            for(CommentEntity commentEntity : optionalCommentEntityList.get()) {
                CommentDTO commentDTO = CommentMapper.INSTANCE.toDTO(commentEntity);
                commentDTOList.add(commentDTO);
            }
            return commentDTOList;
        }
        return commentDTOList;
    }

    public List<ResponseMyTodoDTO> myTodoList(String memberEmail, String date) {

        Optional<List<TodoEntity>> optionalTodoEntityList = todoRepository.findByTodoDateAndTodoEmail(date, memberEmail);
        System.out.println("로그인중인 멤버 이메일 = " + memberEmail + "불러온 날짜" + date +
                "로그인중인 멤버의 불러온 날짜에 맞는 투두들 = " + optionalTodoEntityList);

        List<ResponseMyTodoDTO> myTodoDTOList = new ArrayList<>();

        if (optionalTodoEntityList.isPresent()) {
            for(TodoEntity todoEntity : optionalTodoEntityList.get()) {
                ResponseMyTodoDTO myTodoDTO = TodoMapper.INSTANCE.toMyListDTO(todoEntity);
                myTodoDTO.setComment(comments(myTodoDTO.getId()));
                myTodoDTOList.add(myTodoDTO);
            }
        } else {
            System.out.println(date + "날짜의 투두가 없습니다");
        }
        return myTodoDTOList;
    }
    public ResponseData<TodoResponse> list(Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);
        System.out.println("멤버 id 받아옴 : " + id);

        if (memberEntity.isPresent()) {
            String memberEmail = memberEntity.get().getMemberEmail();
            String today = getDate().getToday();
            String tomorrow = getDate().getTomorrow();

            List<ResponseMyTodoDTO> todayTodoList = myTodoList(memberEmail, today);
            List<ResponseMyTodoDTO> tomorrowTodoList = myTodoList(memberEmail, tomorrow);

            TodoResponse todoResponse = new TodoResponse(todayTodoList, tomorrowTodoList);

            return ResponseData.res(StatusCode.OK, Success.TRUE, todoResponse);
        } else {
            System.out.println("REQUEST 값 오류"); // memberId 에 맞는 memberEntity 가 없음
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }


    public ResponseData<List<AllTodoDTO>> allList(Long id) {
        List<TodoEntity> allTodos = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "todoLike"));
        List<AllTodoDTO> allTodoDTOList = createAllTodoDTOs(allTodos, id);
        return ResponseData.res(StatusCode.OK, Success.TRUE, allTodoDTOList);
    }

    private List<AllTodoDTO> createAllTodoDTOs(List<TodoEntity> todoEntities, Long memberId) {
        List<AllTodoDTO> allTodoDTOList = new ArrayList<>();
        for (TodoEntity todoEntity : todoEntities) {
            Optional<LikeEntity> optionalLikeEntity = likeRepository.findByLikeMemberIdAndLikeTodoId(memberId, todoEntity.getId());
            boolean todoLikeCheck = optionalLikeEntity.isPresent();

            if(todoEntity.isTodoCheck()){
                AllTodoDTO allTodoDTO = TodoMapper.INSTANCE.toAllTodoDTO(todoEntity);
                allTodoDTO.setComment(comments(todoEntity.getId()));
                allTodoDTO.setTodoLikeCheck(todoLikeCheck);

                allTodoDTOList.add(allTodoDTO);
            }
        }
        return allTodoDTOList;
    }


    public ResponseData<List<AllTodoDTO>> searchTitle(TodoDTO todoDTO, Long id) {
        String todoTitle = todoDTO.getTodoTitle();
        List<TodoEntity> TitleTodoDTO = todoRepository.findByTodoTitleContaining(todoTitle).orElse((Collections.emptyList()));
        List<AllTodoDTO> TitleTodoDTOList = createAllTodoDTOs(TitleTodoDTO, id);

        return ResponseData.res(StatusCode.OK, Success.TRUE, TitleTodoDTOList);
    }

    public ResponseData<List<AllTodoDTO>> searchCategory(TodoDTO todoDTO, Long id) {
        String todoCategory = todoDTO.getTodoCategory();
        List<TodoEntity> CategoryTodoDTO = todoRepository.findByTodoCategoryContaining(todoCategory).orElse(Collections.emptyList());
        List<AllTodoDTO> CategoryTodoDTOList = createAllTodoDTOs(CategoryTodoDTO, id);

        return ResponseData.res(StatusCode.OK, Success.TRUE, CategoryTodoDTOList);
    }






    public ResponseData<?> delete(DeleteDTO deleteDTO, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
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


    public ResponseData<TodoEntity> update(UpdateDTO updateDTO, Long memberId) {
        try {

            // UpdateDTO 를 TodoEntity 로 변환
            TodoEntity todoEntity = UpdateMapper.INSTANCE.toEntity(updateDTO);

            // MemberEntity 를 조회
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
            System.out.println("멤버엔티티 : " + optionalMemberEntity);
            if (optionalMemberEntity.isPresent()) {
                String memberEmail = optionalMemberEntity.get().getMemberEmail();
                System.out.println("멤버이메일" + memberEmail);

                Optional<TodoEntity> optionalTodoEntity = todoRepository.findByIdAndTodoEmail(updateDTO.getTodoId(), memberEmail);
                if (optionalTodoEntity.isPresent()) {
                    System.out.println("투두엔티티" + optionalTodoEntity);
                    TodoEntity existingTodo = optionalTodoEntity.get();
                    System.out.println("익사이팅 투두" + existingTodo );
                    // 기존 TodoEntity 에 업데이트할 내용을 설정
                    existingTodo.setTodoTitle(todoEntity.getTodoTitle());
                    existingTodo.setTodoContent(todoEntity.getTodoContent());
                    existingTodo.setTodoCategory(todoEntity.getTodoCategory());
                    existingTodo.setTodoCheck(todoEntity.isTodoCheck());
                    existingTodo.setTodoTime(todoEntity.getTodoTime());

                    // 업데이트된 TodoEntity 를 저장
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