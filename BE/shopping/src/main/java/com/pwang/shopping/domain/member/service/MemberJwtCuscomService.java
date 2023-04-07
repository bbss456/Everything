package com.pwang.shopping.domain.member.service;

import com.pwang.shopping.domain.member.entity.CustumMemberDetail;
import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberJwtCuscomService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow();


        CustumMemberDetail custumMemberDetail = CustumMemberDetail.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();

        return custumMemberDetail;
    }
}
