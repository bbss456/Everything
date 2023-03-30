package com.pwang.shopping.domain.auth.oauth2.naver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.pwang.shopping.domain.auth.jwt.responseDTO.JwtTokenResponseDTO;
import com.pwang.shopping.domain.auth.jwt.service.JwtService;
import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.auth.oauth2.naver.service.MemberServiceOauth2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final MemberServiceOauth2 memberServiceOauth2;
    private final JwtService jwtService;

    public HttpHeaders header() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return  header;
    }

    @GetMapping("/auth/naver")
    public ResponseEntity<JwtTokenResponseDTO> naverToken(@RequestParam String code, @RequestParam String state) throws ParseException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> accessTokenRequest  = memberServiceOauth2.generateAuthCodeRequest(code, state);

        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        HttpEntity<HttpHeaders> profileHttpEntity = memberServiceOauth2.getNaverProfile(accessTokenResponse);
        System.out.println(accessTokenResponse);
        ResponseEntity<String> profileResponse = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                profileHttpEntity,
                String.class
        );

        Member member = memberServiceOauth2.authOrSaveWithGetEmail(profileResponse.getBody());

        return new ResponseEntity<JwtTokenResponseDTO>(jwtService.createToken(member), this.header() , HttpStatus.OK);
    }
}
