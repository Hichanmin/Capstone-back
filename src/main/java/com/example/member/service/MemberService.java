package com.example.member.service;


import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.mapper.MemberMapper;
import com.example.member.repository.MemberRepository;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseData<MemberEntity> save(MemberDTO memberDTO) {
        try {
            MemberEntity memberEntity = MemberMapper.INSTANCE.toEntity(memberDTO);
            memberEntity = memberRepository.save(memberEntity);
            return ResponseData.res(StatusCode.OK, Success.TRUE);
        } catch (Exception e) {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE);
        }
    }

    public ResponseData<MemberDTO> login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {//성공했을때
                memberDTO = MemberMapper.INSTANCE.toDTO(memberEntity);
                return ResponseData.res(StatusCode.OK, Success.TRUE, memberDTO);
            } else {//비밀번호 틀렸을때
                return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, null);
            }
        } else {//아예아이디도없을때
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, null);
        }
    }

    public boolean verify(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        return byMemberEmail.isEmpty();
    }

    public ResponseData<MemberDTO> updateMemberCredentials(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            memberEntity.setMemberName(memberDTO.getMemberName());
            memberEntity.setMemberPassword(memberDTO.getMemberPassword());
            memberEntity = memberRepository.save(memberEntity);

            return ResponseData.res(StatusCode.OK, Success.TRUE, MemberMapper.INSTANCE.toDTO(memberEntity));
        } else {
            return ResponseData.res(StatusCode.BAD_REQUEST, Success.FALSE, null);
        }
        //커밋
    }

}






