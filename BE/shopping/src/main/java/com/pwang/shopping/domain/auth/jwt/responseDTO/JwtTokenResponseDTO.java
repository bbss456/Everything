package com.pwang.shopping.domain.auth.jwt.responseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenResponseDTO {

    private String accessToken;
    private String refreshToken;
}
