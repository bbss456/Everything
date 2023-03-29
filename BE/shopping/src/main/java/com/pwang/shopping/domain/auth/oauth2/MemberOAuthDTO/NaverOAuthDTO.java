package com.pwang.shopping.domain.auth.oauth2.MemberOAuthDTO;

import lombok.Data;

@Data
public class NaverOAuthDTO {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
}
