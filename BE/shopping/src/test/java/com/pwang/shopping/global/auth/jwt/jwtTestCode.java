package com.pwang.shopping.global.auth.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class jwtTestCode {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test()
    public void createJwt() {
        String jwtToken = jwtTokenProvider.createAccessToken("emwer", "admin");
        Assertions.assertThat("").isEqualTo(jwtToken);
    }
}
