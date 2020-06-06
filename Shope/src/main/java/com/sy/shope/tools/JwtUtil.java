package com.sy.shope.tools;

import com.sy.shope.support.OrderingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * jwt
 * @author wangxiao
 * @since 1.1
 */
@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.expire}")
    private long expire;

    @Value(("${jwt.secret}"))
    private String secret;


    public String createToken (String userId){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getTokenUserId (String token) {
        return getTokenClaim(token).getSubject();
    }

    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            throw  new OrderingException("500","token 异常");
        }
    }

    public boolean isTokenExpired (String token) {
        return getTokenClaim(token).getExpiration().before(new Date());
    }


}
