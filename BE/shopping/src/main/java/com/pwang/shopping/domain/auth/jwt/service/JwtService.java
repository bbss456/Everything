package com.pwang.shopping.domain.auth.jwt.service;

import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.global.config.jwt.JwtTokenProvider;
import com.pwang.shopping.domain.auth.jwt.responseDTO.JwtTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenResponseDTO createToken(Member member) {

        return JwtTokenResponseDTO.builder()
                .accessToken(jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().toString()))
                .refreshToken(jwtTokenProvider.createJwtRefreshToken(member.getEmail()))
                .build();
    }

}
