package com.example.greenlast.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

// JWT 생성 및 검증 담당 클래스
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}") // application.properties에서 jwt.secret 값을 가져옴
    private String secretKey;

    @Value("${jwt.expiration}") // application.properties에서 jwt.expiration 값을 가져옴
    private long expiration;

    // JWT 생성
    public String createToken(String userId, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(userId) // user_id 저장
                .claim("role", role) // 사용자 역할 저장
                .setIssuedAt(now)//발급기간
                .setExpiration(validity) // 유효기간
                .signWith(SignatureAlgorithm.HS256, secretKey) //비밀키서명
                .compact();
    }


    // JWT 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // 비밀 키로 검증
            return true; // 유효하면 true 반환
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 유효하지 않으면 false 반환
        }
    }

    // JWT에서 사용자 이름 추출
    public String getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // JWT에서 사용자 역할(Role) 추출
    public String getRole(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}
