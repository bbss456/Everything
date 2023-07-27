package com.pwang.shopping.domain.member.service;

import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.entity.OAuthType;
import com.pwang.shopping.domain.member.repository.MemberRepository;
import com.pwang.shopping.domain.member.requestdto.MemberCreateRequestDTO;
import com.pwang.shopping.domain.member.responsedto.ResponseMemberDTO;
import com.pwang.shopping.domain.member.responsedto.ResponseMemberListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class Memberservice {

    private final MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean create(MemberCreateRequestDTO memberCreateRequestDTO) {
        Member member = Member.builder()
                .email(memberCreateRequestDTO.getEmail())
                .name(memberCreateRequestDTO.getName())
                .password(passwordEncoder.encode(memberCreateRequestDTO.getPassword()))
                .build();

        memberRepository.save(member);
        return Boolean.TRUE;
    }

    public Member findMember(String email, String type) {
        OAuthType typeEnum = OAuthType.valueOf(type);
        Member member= memberRepository.findByEmailAndType(email, typeEnum).orElseThrow();
        return member;
    }

    public ResponseMemberListDTO findAllMember(Pageable pageable) {

        Page<Member> memberList =  memberRepository.findAll(pageable);

        List<ResponseMemberDTO> responseMemberDTOList = memberList.getContent().stream().map(ResponseMemberDTO::new).collect(Collectors.toList());

        ResponseMemberListDTO responseMemberListDTO = new ResponseMemberListDTO();
        responseMemberListDTO.setMemberList(responseMemberDTOList);
        responseMemberListDTO.setCount(responseMemberDTOList.size());

        return responseMemberListDTO;
    }
}