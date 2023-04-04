package com.pwang.shopping.global.auth.jwt;

import com.pwang.shopping.ShoppingApplication;
import com.pwang.shopping.global.config.jwt.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShoppingApplication.class )
public class jwtTestCode {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test()
    @DisplayName("JWT_생성")
    public void createJwt() {
        String jwtToken = jwtTokenProvider.createAccessToken("emwer", "admin");

        Boolean result = Boolean.TRUE;
        if(jwtToken.equals("")) {
           result = Boolean.FALSE;
        }

        Assertions.assertThat(result).isEqualTo(Boolean.TRUE);
    }
}
