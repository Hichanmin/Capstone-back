package com.example.member.service;


import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.entity.TodoEntity;
import com.example.member.mapper.MemberMapper;
import com.example.member.mapper.UpdateMapper;
import com.example.member.repository.MemberRepository;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import lombok.*;
import org.springframework.stereotype.Service;
import org.mapstruct.Mapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public ResponseData<MemberEntity> save(MemberDTO memberDTO) {
        try {
            // MemberDTO를 MemberEntity로 변환
            MemberEntity memberEntity = MemberMapper.INSTANCE.toEntity(memberDTO);

            // MemberEntity를 저장
            memberEntity = memberRepository.save(memberEntity);

            // 저장된 MemberEntity를 포함하여 성공 응답 반환
            return ResponseData.res(StatusCode.OK, Success.TRUE, memberEntity);
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }

    public ResponseData<MemberDTO> login(MemberDTO memberDTO) {
        // 이메일로 사용자 찾기
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        // 사용자가 존재하는지 확인
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();

            // 평문 비밀번호 비교
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 로그인 성공, 엔티티를 DTO로 변환
                memberDTO = MemberMapper.INSTANCE.toDTO(memberEntity);
                return ResponseData.res(StatusCode.OK, Success.TRUE, memberDTO);
            } else {
                // 비밀번호 불일치
                return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
            }
        } else {
            // 이메일로 사용자 찾을 수 없음
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }


    public boolean verify(MemberDTO memberDTO) {
        try {
            Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
            return byMemberEmail.isEmpty();
        } catch (Exception e) {
            // 예외 발생 시 처리
            return false; // 예외 발생 시 회원이 존재하지 않음으로 간주
        }
    }



    public ResponseData<MemberDTO> updateMemberCredentials(MemberDTO memberDTO) {
        // 회원 ID로 기존 회원 찾기
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(memberDTO.getId());

        if (memberEntityOptional.isPresent()) {
            MemberEntity memberEntity = memberEntityOptional.get();

            // 회원 정보 업데이트
            memberEntity.setMemberName(memberDTO.getMemberName());
            memberEntity.setMemberPassword(memberDTO.getMemberPassword());

            // 업데이트된 회원 정보를 저장
            memberRepository.save(memberEntity);

            // 엔티티를 DTO로 변환
            memberDTO = MemberMapper.INSTANCE.toDTO(memberEntity);

            // 성공 응답 반환
            return ResponseData.res(StatusCode.OK, Success.TRUE, memberDTO);
        } else {
            // 회원을 찾을 수 없을 때
            return ResponseData.res(StatusCode.NOT_FOUND, Success.FALSE);
        }
    }

    /*public ResponseData<MemberDTO> getMemberById(Long id) {
        // 주어진 ID로 회원 정보 조회
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(id);

        if (memberEntityOptional.isPresent()) {
            MemberEntity member = memberEntityOptional.get();

            // 엔티티를 DTO로 변환
            MemberDTO memberDTO = MemberMapper.INSTANCE.toDTO(member);

            // 성공 응답 반환
            return ResponseData.res(StatusCode.OK, Success.TRUE, memberDTO);
        } else {
            // 회원 정보를 찾을 수 없을 때
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }*/

}








