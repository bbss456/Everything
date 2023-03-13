package com.pwang.shopping.config.security.oauth2;



import com.pwang.shopping.domain.member.service.MemberServiceOauth2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final MemberServiceOauth2 memberServiceOauth2;

    @GetMapping("/auth/naver")
    public String naverToken(@RequestParam String code, @RequestParam String state) {
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

        return "accessToken: " + accessTokenResponse.getBody();
    }

    @GetMapping("/login")
    public String test(@RequestParam String code, @RequestParam String state) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest  = memberServiceOauth2.generateAuthCodeRequest(code, state);
        System.out.println("asd");
        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        return "Success";
    }

}
