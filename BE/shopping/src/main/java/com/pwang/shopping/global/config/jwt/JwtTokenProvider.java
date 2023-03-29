package com.pwang.shopping.global.config.jwt;

import com.pwang.shopping.domain.member.entity.CustumMemberDetail;
import com.pwang.shopping.domain.member.service.MemberJwtCuscomService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static String secretKey = "!shoppingMALLPWANG@";
    private static String refreshSecretKey = "!REshoppingMALLPWANG@";

    private static long tokenValidTime = 60 * 60 * 1 * 500L ;

    private static long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 2 * 1000L;

    private final MemberJwtCuscomService memberJwtCuscomService;

    public static String createAccessToken(String email, String roles) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("ROLE", roles);
        Date now = new Date();

        return "Bearae " + Jwts.builder()
                .setIssuer("ShoppingMallPwnag")
                .setSubject("shoppingMallToken")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String createJwtRefreshToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return "Bearae " +Jwts.builder()
                .setIssuer("ShoppingMallPwnag")
                .setSubject("shoppingMallToken")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        CustumMemberDetail  custumMemberDetail = (CustumMemberDetail) memberJwtCuscomService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(custumMemberDetail, "", custumMemberDetail.getAuthorities());
    }

//    public Authentication getAuthenticationRefreshToken(String refreshToken) {
//        User user = user.findwithrefreshToken(getClaimsRefresh(refreshToken).get("value").toString()).orElseThrow(() -> new BusinessExceptionHandler("토큰이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
//        UserDetails userDetails = userDetailsService.loadUserByUsername(api.getApiId());
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("RefreshToken");
    }

    public boolean validateToken(ServletRequest request, String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "IllegalArgumentException");
        } catch (SignatureException e) {
            request.setAttribute("exception", "SignatureException");
        }
        return false;
    }

    public boolean validateRefreshToken(ServletRequest request, String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "IllegalArgumentException");
        } catch (SignatureException e) {
            request.setAttribute("exception", "SignatureException");
        }
        return false;
    }

    public static Claims getClaimsRefresh(String jwtToken) throws JwtException {
        return Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(jwtToken).getBody();
    }
}