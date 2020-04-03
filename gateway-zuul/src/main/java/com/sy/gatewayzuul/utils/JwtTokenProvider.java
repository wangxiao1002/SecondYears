package com.sy.gatewayzuul.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;

/**
 * @author wangxiao
 * @description: //TODO
 * @date 2020/3/29
 */
@Component
public class JwtTokenProvider {



    public static final String COOKIE_NAME = "JWT_TOKEN";

    private final String KEY = "JWT_TOKEN";

    private final String issUser = "gateWay-zuul";

    private long expTime = 10*60*1000;


    public String createToken(String userCode) {
        long expire = Instant.now().toEpochMilli() + expTime;
        return Jwts.builder()
                .setHeaderParam("jwt", "jwt")
                .setSubject(userCode)
                .setIssuer(issUser)
                .setExpiration(new Date(expire))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .claim("userCode",userCode)
                .compact();
    }

    public void setCookie(HttpServletResponse response,String token) {
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setMaxAge(-1);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getUserCode(String token){
        return getTokenClaim(token).get("userCode",String.class);
    }
    public boolean isTokenExpired (String token) {
        return getTokenClaim(token).getExpiration().before(new Date());
    }

    public String refreshToken(String token) {
        String userCode = getUserCode(token);
        return createToken(userCode);
    }


}
