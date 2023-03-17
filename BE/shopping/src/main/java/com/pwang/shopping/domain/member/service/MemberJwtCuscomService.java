package com.pwang.shopping.domain.member.service;

import com.pwang.shopping.domain.member.entity.CustumMemberDetail;
import com.pwang.shopping.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberJwtCuscomService implements UserDetailsService {

    private final Memberservice memberservice;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberservice.findMember(email);


        CustumMemberDetail custumMemberDetail = CustumMemberDetail.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();

        return custumMemberDetail;
    }
}
