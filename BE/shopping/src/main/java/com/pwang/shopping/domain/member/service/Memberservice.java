package com.pwang.shopping.domain.member.service;

import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.repository.MemberRepository;
import com.pwang.shopping.domain.member.requestDTO.MemberCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Member findMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        return member;
    }

}
