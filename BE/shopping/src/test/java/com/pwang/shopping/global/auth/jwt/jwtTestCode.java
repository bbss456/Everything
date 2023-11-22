package com.pwang.shopping.global.auth.jwt;

import com.pwang.shopping.ShoppingApplication;
import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.entity.Role;
import com.pwang.shopping.domain.member.repository.MemberRepository;
import com.pwang.shopping.global.config.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShoppingApplication.class )
public class jwtTestCode {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberRepository memberRepository;

    @Test()
    @DisplayName("JWT_생성")
    @Transactional
    public void createJwt() {

        Member member = Member
                .builder()
                .name("test")
                .password("")
                .gender("M")
                .role(Role.USER)
                .mobile("010-3078-1207")
                .birthyear("1994")
                .email("bbss1207@naver.com")
                .build();

        memberRepository.save(member);
        String jwtToken = jwtTokenProvider.createAccessToken(member);
        System.out.println(jwtToken);

        Boolean result = Boolean.TRUE;
        if(jwtToken.equals("")) {
           result = Boolean.FALSE;
        }
        assertEquals(result, Boolean.TRUE);
    }

    @Test
    @DisplayName("토큰_ID 가져오기")
    public void getUserPk(){
        String secretKey = "!shoppingMALLPWANG@";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImJic3MxMjA3QG5hdmVyLmNvbSIsIlJPTEUiOiJVU0VSIiwiSUQiOiI2MTU2ZDc5Ny03M2MwLTQ5MzEtYjA4MS03NzVlYmQ0NWUzNjQiLCJpYXQiOjE2ODA4NjA0MzYsImV4cCI6MTY4MDg2MjIzNn0.BsnKyhI5snEsHM9f9ssw-ggH3RQSZfSKSLC6VqfLXdE";
        Claims jwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        String id = (String) jwt.get("ID");
        Boolean result = Boolean.TRUE;
        if(id.equals("")) {
            result = Boolean.FALSE;
        }

        assertEquals(result, Boolean.TRUE);
    }
}