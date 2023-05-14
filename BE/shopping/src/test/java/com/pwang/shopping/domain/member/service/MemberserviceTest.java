package com.pwang.shopping.domain.member.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.pwang.shopping.ShoppingApplication;
import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.entity.OAuthType;
import com.pwang.shopping.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShoppingApplication.class)
class MemberserviceTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("네이버_생성_및_조회")
    void saveMember() {
        Member member = new Member();
        JSONObject jsonObject = new JSONObject();
        String email = "sky@nav2er.com";
        jsonObject.put("email", email);
        jsonObject.put("name", "ktko");
        jsonObject.put("gender", "M");
        jsonObject.put("birthyear", "1994");
        jsonObject.put("mobile", "010-3078-1207");


        member = memberRepository.findByEmailAndType(email, OAuthType.NAVER)
                .orElse(member.makeEntityOfNaver(jsonObject));

        memberRepository.save(member);
        assertEquals(member.getEmail(), email);
    }

    @Test
    @DisplayName("회원멤버_조회")
    void findMember() {
        String email = "sky@naver.com";
        Member member= memberRepository.findByEmailAndType(email, OAuthType.NAVER).orElseThrow();

        boolean result = true;
        System.out.println(member.getId());

        if(member.getId().equals("")) {
            result = false;
        }
        assertEquals(result, true);
    }

    @Test
    @DisplayName("전체회원멤버_조회")
    void findByAllMember() {

       List<Member> memberList = memberRepository.findAll();
    }

    @Test
    @DisplayName("회원멤버_삭제")
    void deleteMembeer() {
        String email = "sky@naver.com";
        memberRepository.delete(memberRepository.findByEmailAndType(email, OAuthType.NAVER)
                .orElseThrow()
        );
    }
}