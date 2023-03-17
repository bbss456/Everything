package com.pwang.shopping.global.auth.oauth2;

import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.entity.Role;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;
    private final String gender;
    private final String age;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private  String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private  String clientSecret;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        else {
            // 추후 다른 Oauth2 개발시 분기 처리
            return ofNaver("id", attributes);
        }
    }


    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }

}