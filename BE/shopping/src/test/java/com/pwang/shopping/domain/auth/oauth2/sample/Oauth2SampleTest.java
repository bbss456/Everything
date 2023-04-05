package com.pwang.shopping.domain.auth.oauth2.sample;

import com.nimbusds.jose.Header;
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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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

    @Test
    @DisplayName("구글_회원정보")
    void generateGoogleAuthCodeRequest() throws ParseException {
        String token = "ya29.a0Ael9sCMKIwz_LJn6XmoJFZ-nvmVB2HpsP3mNsrH6CqfMSr-BovkPmcCmKeRb21y0EJCNh5KvdO4poGTgN5BEbkwpfRx0GjBeXmAhss3eMajGg9odXFmrEJ_nQL1wKcHt1H9g_a40sHoxObfX55xFWeNyYwjvaCgYKAWISARMSFQF4udJhnu2KVYszWnfO-68fhmz1Zg0163";
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_token", token)
                .build(true);

        HttpEntity<Header> accessTokenRequest = new HttpEntity<>(headers);
        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                uriBuilder.toString(),
                HttpMethod.GET,
                accessTokenRequest,
                String.class
        );
        System.out.println(accessTokenResponse);
        assertEquals("200 OK",accessTokenResponse.getStatusCode().toString());
    }
}
