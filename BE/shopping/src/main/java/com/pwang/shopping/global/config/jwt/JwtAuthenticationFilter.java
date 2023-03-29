package com.pwang.shopping.global.config.jwt;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);

        if (token != null && jwtTokenProvider.validateToken(request, token)) {
            try {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException error) {
                request.setAttribute("exception", "MalformedJwtException");
            }
        }

//        if (refreshToken != null && jwtTokenProvider.validateRefreshToken(request, refreshToken)) {
//            try {
//                //Authentication authentication = jwtTokenProvider.getAuthenticationRefreshToken(refreshToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (RuntimeException error) {
//                request.setAttribute("exception", "MalformedJwtException");
//            }
//        }
        chain.doFilter(request, response);
    }
}