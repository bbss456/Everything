package com.pwang.shopping.domain.auth.oauth2.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.pwang.shopping.ShoppingApplication;
import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.entity.OAuthType;
import com.pwang.shopping.domain.member.repository.MemberRepository;
import com.pwang.shopping.global.config.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShoppingApplication.class)
class MemberServiceOauth2Test {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("네이버_조회_및_저장")
    @Transactional
    public void naverAuthOrSaveWithGetEmail() throws ParseException {

        String email = "test@naver.com";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", "testpwd");
        jsonObject.put("name", "TEST_NAME");
        jsonObject.put("gender", "M");
        jsonObject.put("birthyear", "1994");
        jsonObject.put("mobile", "010-3078-1207");

        Member member = new Member();

        member = memberRepository.findByEmailAndType(email, OAuthType.NAVER)
                .orElse(member.makeEntityOfNaver(jsonObject));

        memberRepository.save(member);

        assertEquals(email, member.getEmail());
    }

}