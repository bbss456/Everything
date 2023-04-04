package com.pwang.shopping.domain.auth.oauth2.sample;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Oauth2SampleTest {

    @Test
    @DisplayName("카카오_토큰발급")
    void generateAuthCodeRequest() {

        String clientId = "953f431e46da04f08274b90f73ff43a0";
        String redirect_url="http://localhost:8081/login/oauth2/code/kakao";
        String code = "_RqB1w5MqI7lOh6PiqkyP8dQEoEZJicx2d_QSOAQ2FIlx4Iseo0UZyIWi_VafookbSRW6wopyNoAAAGHTDQ1Kw";
        String state = "1324";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> paramList = new LinkedMultiValueMap<>();
        paramList.add("grant_type", "authorization_code");
        paramList.add("client_id", clientId);
        paramList.add("redirect_uri", redirect_url);
        paramList.add("code", code);
        paramList.add("state", state);

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(paramList, headers);


        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );
        System.out.println(accessTokenResponse.getBody());
        assertEquals("200 OK",accessTokenResponse.getStatusCode().toString());
    }

    @Test
    @DisplayName("카카오_회원정보")
    void getKakkoMemberInfo() throws ParseException {

        String accesstoken = "PVtizloM2mslJ05zj6jQ44goBtRjgyB76ogR9VeECj11nAAAAYdHI2dj";
        HttpHeaders profileRequestHeader = new HttpHeaders();
        profileRequestHeader.add("Authorization", "Bearer " + accesstoken);
        HttpEntity<HttpHeaders> accessTokenRequest = new HttpEntity<>(profileRequestHeader);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );
        System.out.println(accessTokenResponse);
        JSONParser parser = new JSONParser();
        JSONObject kakaojsonObject = (JSONObject) parser.parse(accessTokenResponse.getBody());
        System.out.println(kakaojsonObject);
        kakaojsonObject = (JSONObject) kakaojsonObject.get("kakao_account");
        String email = (String) kakaojsonObject.get("email");
        System.out.println(email);

        assertEquals("200 OK",accessTokenResponse.getStatusCode().toString());
    }
}
