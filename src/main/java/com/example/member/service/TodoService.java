package com.example.member.service;

import java.time.LocalDate;

import com.example.member.dto.*;
import com.example.member.entity.LikeEntity;
import com.example.member.entity.MemberEntity;
import com.example.member.entity.TodoEntity;
import com.example.member.mapper.TodoMapper;
import com.example.member.repository.LikeRepository;
import com.example.member.repository.MemberRepository;
import com.example.member.repository.TodoRepository;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import com.example.member.response.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;

    Logger logger = LoggerFactory.getLogger(TodoService.class);

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

    public ResponseData<TodoEntity> save(TodoDTO todoDTO) {
        try {
            TodoEntity todoEntity = TodoMapper.INSTANCE.toEntity(todoDTO);
            todoRepository.save(todoEntity);
            return ResponseData.res(StatusCode.OK, Success.TRUE);
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }

    public List<ResponseMyTodoDTO> myTodoList(String memberEmail, String date) {
        Optional<List<TodoEntity>> optionalTodoEntityList = todoRepository.findByTodoDateAndTodoEmail(date, memberEmail);
        System.out.println("로그인중인 멤버 이메일 = " + memberEmail + "불러온 날짜" + date +
                "로그인중인 멤버의 불러온 날짜에 맞는 투두들 = " + optionalTodoEntityList);

        List<ResponseMyTodoDTO> myTodoDTOList = new ArrayList<>();

        if (optionalTodoEntityList.isPresent()) {
            for(TodoEntity todoEntity : optionalTodoEntityList.get()) {
                ResponseMyTodoDTO myTodoDTO = TodoMapper.INSTANCE.toMyListDTO(todoEntity);
                myTodoDTOList.add(myTodoDTO);
            }
            return myTodoDTOList;
        } else {
            // 날짜에 맞는 투두가 없을 때
            System.out.println(date + "날짜의 투두가 없습니다");
            return myTodoDTOList = null;
        }
    }
    public ResponseData<TodoResponse> list(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberDTO.getId());
        System.out.println("멤버 id 받아옴 : " + memberDTO.getId());

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



//    public ResponseData<Void> delete(String todoDate, String todoEmail) {
//        try {
//            // todoRepository에서 해당하는 todoEntity를 찾아 삭제
//            TodoEntity todoEntity = todoRepository.findByTodoDateAndTodoEmail(todoDate, todoEmail)
//                    .orElseThrow(() -> new IllegalArgumentException("Todo 항목을 찾을 수 없습니다. Date: " + todoDate + ", Email: " + todoEmail));
//            todoRepository.delete(todoEntity);
//            return ResponseData.res(StatusCode.OK, Success.TRUE);
//        } catch (IllegalArgumentException e) {
//            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
//        } catch (Exception e) {
//            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
//        }
//    }
//   같은날에 todo가 여러개 있을 수 있음



//    public ResponseData<TodoEntity> update(String todoDate, String todoEmail, TodoDTO todoDTO) {
//        try {
//            // todoRepository에서 해당하는 todoEntity를 찾아 업데이트
//            TodoEntity todoEntity = todoRepository.findByTodoDateAndTodoEmail(todoDate, todoEmail)
//                    .orElseThrow(() -> new IllegalArgumentException("Todo 항목을 찾을 수 없습니다. Date: " + todoDate + ", Email: " + todoEmail));
//
//            todoEntity.setTodoTitle(todoDTO.getTodoTitle());
//            todoEntity.setTodoContent(todoDTO.getTodoContent());
//            todoEntity.setTodoCategory(todoDTO.getTodoCategory());
//            todoEntity.setTodoLikes(todoDTO.getTodoLikes());
//            todoEntity.setTodoCheck(todoDTO.isTodoCheck());
//
//            TodoEntity updatedTodo = todoRepository.save(todoEntity);
//            return ResponseData.res(StatusCode.OK, Success.TRUE, updatedTodo);
//        } catch (IllegalArgumentException e) {
//            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
//        } catch (Exception e) {
//            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
//        }
//    }


}