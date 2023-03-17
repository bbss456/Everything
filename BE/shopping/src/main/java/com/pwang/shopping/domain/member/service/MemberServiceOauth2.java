package com.pwang.shopping.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.pwang.shopping.global.auth.oauth2.MemberOAuthDTO.NaverOAuthDTO;
import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class MemberServiceOauth2 {

    private final MemberRepository memberRepository ;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private  String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private  String clientSecret;

    public HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String code, String state) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> paramList = new LinkedMultiValueMap<>();
        paramList.add("grant_type", "authorization_code");
        paramList.add("client_id", clientId);
        paramList.add("client_secret", clientSecret);
        paramList.add("code", code);
        paramList.add("state", state);

        return new HttpEntity<>(paramList, headers);
    }

    public HttpEntity<HttpHeaders> getNaverProfile(ResponseEntity<String> accessTokenResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NaverOAuthDTO naverOAuthDTO = objectMapper.readValue(accessTokenResponse.getBody(), NaverOAuthDTO.class);

        HttpHeaders profileRequestHeader = new HttpHeaders();
        profileRequestHeader.add("Authorization", "Bearer " + naverOAuthDTO.getAccess_token());

        return new HttpEntity<>(profileRequestHeader);
    }

    public Member authOrSaveWithGetEmail(String profile) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject naverjsonObject = (JSONObject) parser.parse(profile);
        naverjsonObject = (JSONObject) naverjsonObject.get("response");
        String email = (String) naverjsonObject.get("email");
        String name = (String) naverjsonObject.get("name");

        Member member = new Member();
        member = memberRepository.findByEmail(email)
                .orElse(member.makeEntity(email, name));
        memberRepository.save(member);

        return member;
    }
}