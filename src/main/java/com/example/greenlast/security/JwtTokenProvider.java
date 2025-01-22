package com.example.greenlast.security;

/**
 * Created on 2025-01-22 by 한상인
 *
 * 이 클래스는 JWT 토큰을 생성하거나, 검증하거나, 정보를 추출하는 데 사용됩니다.
 * JWT는 사용자 인증에 사용되는 특별한 문자열입니다.
 */

import io.jsonwebtoken.*; // JWT 생성, 검증, 파싱 등의 기능을 제공하는 라이브러리
import org.springframework.beans.factory.annotation.Value; // application.properties의 값을 가져오기 위한 어노테이션
import org.springframework.stereotype.Component; // Spring에서 이 클래스를 사용할 수 있도록 등록

import java.util.Date; // 날짜와 시간을 다루기 위한 클래스

@Component // Spring이 이 클래스를 관리하도록 알려줍니다.
public class JwtTokenProvider {

    @Value("${jwt.secret}") // application.properties에서 jwt.secret 값을 가져옵니다.
    private String secretKey; // JWT를 암호화하고 검증하는 데 사용하는 비밀 키

    @Value("${jwt.expiration}") // application.properties에서 jwt.expiration 값을 가져옵니다.
    private long expiration; // JWT가 만료되기까지의 시간 (밀리초 단위)

    // 토큰 생성 메서드
    public String createToken(String username, String role) {
        Date now = new Date(); // 현재 시간
        Date validity = new Date(now.getTime() + expiration); // 만료 시간 계산

        // JWT 생성
        return Jwts.builder()
                .setSubject(username) // 사용자 이름을 토큰에 저장
                .claim("role", role) // 사용자의 권한(Role) 정보를 추가로 저장
                .setIssuedAt(now) // 토큰이 발급된 시간
                .setExpiration(validity) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256 알고리즘과 비밀 키로 서명
                .compact(); // 최종적으로 JWT 문자열 반환
    }

    // 토큰 검증 메서드
    public boolean validateToken(String token) {
        try {
            // 토큰이 유효한지 확인
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true; // 유효하다면 true 반환
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰이 잘못되었거나 만료되었을 때 예외 발생
            return false; // 유효하지 않다면 false 반환
        }
    }

    // 토큰에서 사용자 이름 추출
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // 비밀 키를 사용하여 토큰 검증
                .parseClaimsJws(token) // 토큰을 파싱하여 Claims(데이터 부분) 추출
                .getBody() // 토큰의 본문 가져오기
                .getSubject(); // 사용자 이름 반환
    }

    // 토큰에서 Role 정보 추출
    public String getRole(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // 비밀 키를 사용하여 토큰 검증
                .parseClaimsJws(token) // 토큰을 파싱하여 Claims(데이터 부분) 추출
                .getBody() // 토큰의 본문 가져오기
                .get("role", String.class); // "role"에 저장된 값(권한) 반환
    }
}
