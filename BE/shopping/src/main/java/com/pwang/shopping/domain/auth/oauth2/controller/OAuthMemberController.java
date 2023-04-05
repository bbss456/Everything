package com.pwang.shopping.domain.auth.oauth2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.Header;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.pwang.shopping.domain.auth.jwt.responseDTO.JwtTokenResponseDTO;
import com.pwang.shopping.domain.auth.jwt.service.JwtService;
import com.pwang.shopping.domain.auth.oauth2.service.MemberServiceOauth2;
import com.pwang.shopping.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class OAuthMemberController {

    private final MemberServiceOauth2 memberServiceOauth2;
    private final JwtService jwtService;

    public HttpHeaders header() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return  header;
    }

    @GetMapping("/auth/naver")
    public ResponseEntity<JwtTokenResponseDTO> createNaverJwtToken(@RequestParam String code, @RequestParam String state) throws ParseException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

        String type = "NAVER";

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest  = memberServiceOauth2.generateAuthCodeRequest(code, state, type);

        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        HttpEntity<HttpHeaders> profileHttpEntity = memberServiceOauth2.getProfile(accessTokenResponse);
        ResponseEntity<String> profileResponse = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                profileHttpEntity,
                String.class
        );

        Member member = memberServiceOauth2.naverAuthOrSaveWithGetEmail(profileResponse.getBody());
        return new ResponseEntity<JwtTokenResponseDTO>(jwtService.createToken(member), this.header() , HttpStatus.OK);
    }

    @GetMapping("/auth/kakao")
    public ResponseEntity<JwtTokenResponseDTO> createKakaoJwtToken(@RequestParam String code, @RequestParam String state) throws ParseException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

        String type = "KAKAO";
        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = memberServiceOauth2.generateAuthCodeRequest(code, state, type);

        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        HttpEntity<HttpHeaders> profileHttpEntity = memberServiceOauth2.getProfile(accessTokenResponse);

        ResponseEntity<String> profileResponse = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                profileHttpEntity,
                String.class
        );

//        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("userId", 1)
//                .queryParam("id", 1)
//                .build(true);

        Member member = memberServiceOauth2.kakaoAuthOrSaveWithGetEmail(profileResponse.getBody());
        return new ResponseEntity<JwtTokenResponseDTO>(jwtService.createToken(member), this.header(), HttpStatus.OK);
    }

    @GetMapping("/auth/google")
    public ResponseEntity<JwtTokenResponseDTO> getUserInfo(@RequestParam String token) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_token", token)
                .build(true);

        HttpEntity<Header> accessTokenRequest = new HttpEntity<>(headers);
        ResponseEntity<String> profileResponse = restTemplate.exchange(
                uriBuilder.toString(),
                HttpMethod.GET,
                accessTokenRequest,
                String.class);

        Member member = memberServiceOauth2.googleAuthOrSaveWithGetEmail(profileResponse.getBody());
        return new ResponseEntity<>(jwtService.createToken(member), this.header(), HttpStatus.OK);
    }

}
