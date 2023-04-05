package com.pwang.shopping.domain.auth.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.pwang.shopping.domain.auth.oauth2.MemberOAuthDTO.NaverOAuthDTO;
import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.entity.OAuthType;
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
    private  String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private  String naverClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private  String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private  String kakaoRedirect_url;

    public HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String code, String state, String type) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> paramList = new LinkedMultiValueMap<>();

        paramList.add("grant_type", "authorization_code");
        paramList.add("code", code);
        paramList.add("state", state);

        if(type.equals("NAVER")) {
            paramList.add("client_id", naverClientId);
            paramList.add("client_secret", naverClientSecret);

        } else if (type.equals("KAKAO")) {
            paramList.add("client_id", kakaoClientId);
            paramList.add("redirect_uri", kakaoRedirect_url);
        }

        return new HttpEntity<>(paramList, headers);
    }


    public HttpEntity<HttpHeaders> getProfile(ResponseEntity<String> accessTokenResponse)
            throws JsonProcessingException, ParseException {

        System.out.println(kakaoClientId);
        System.out.println(kakaoRedirect_url);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(accessTokenResponse.getBody());
        String accessToken = (String) jsonObject.get("access_token");
        System.out.println(accessTokenResponse.getBody());
        System.out.println(accessToken);
        HttpHeaders profileRequestHeader = new HttpHeaders();
        profileRequestHeader.add("Authorization", "Bearer " + accessToken);

        return new HttpEntity<>(profileRequestHeader);
    }

    public Member naverAuthOrSaveWithGetEmail(String profile) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject naverjsonObject = (JSONObject) parser.parse(profile);
        naverjsonObject = (JSONObject) naverjsonObject.get("response");
        String email = (String) naverjsonObject.get("email");
        Member member = new Member();


        member = memberRepository.findByEmailAndType(email, OAuthType.NAVER)
                .orElse(member.makeEntityOfNaver(naverjsonObject));
        memberRepository.save(member);

        return member;
    }

    public Member kakaoAuthOrSaveWithGetEmail(String profile) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject kakaojsonObject = (JSONObject) parser.parse(profile);
        kakaojsonObject = (JSONObject) kakaojsonObject.get("kakao_account");
        String email = (String) kakaojsonObject.get("email");
        Member member = new Member();
        member = memberRepository.findByEmailAndType(email,OAuthType.KAKAO)
                .orElse(member.makeEntityOfKakao(kakaojsonObject));
        memberRepository.save(member);

        return member;
    }
    public Member googleAuthOrSaveWithGetEmail(String profile) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject googleJsonObect = (JSONObject) parser.parse(profile);
        String email =(String) googleJsonObect.get("email");
        Member member = new Member();
        member = memberRepository.findByEmailAndType(email,OAuthType.GOOGLE)
                .orElse(member.makeEntityOfGoogle(googleJsonObect));
        memberRepository.save(member);

        return member;
    }
}