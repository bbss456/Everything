package com.pwang.shopping.global.config.jwt;

import java.io.IOException;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.pwang.shopping.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        String exception = (String) request.getAttribute("exception");
        ErrorCode errorCode;
        log.warn(exception);

        String result = (String) request.getAttribute("refresh");

        if(exception == null) {
            errorCode = ErrorCode.NULLTokenException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("MalformedJwtException")) {
            errorCode = ErrorCode.UNAUTHORIZEDException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("IllegalArgumentException")) {
            errorCode = ErrorCode.NULLTokenException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("ExpiredJwtException")) {
            errorCode = ErrorCode.ExpiredJwtException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("SignatureException")) {
            errorCode = ErrorCode.UNAUTHORIZEDException;
            setResponse(response, errorCode);
            return;
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        json.put("status", errorCode.getStatus());
        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());
        response.getWriter().print(json);
    }
}