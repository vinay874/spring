package com.pvkr.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private String signature_key = "afjsadlfajf2rt523423wfasflajsfaslf08t2345";
    private final int EXPIRY_TIME = 1000 * 60 * 60;
    private Key key = Keys.hmacShaKeyFor(signature_key.getBytes());

    public String generateJwtToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME))
                .signWith(key)
                .compact();
    }

    public String extraUsername(String token){
        return parseClaims(token).getSubject();
    }

    public Boolean isTokenExpried(String token){
        return parseClaims(token).getExpiration().after(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
