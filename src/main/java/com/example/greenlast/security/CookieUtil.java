package com.example.greenlast.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

// 쿠키를 생성, 삭제하는 유틸리티 클래스
public class CookieUtil {

    // JWT를 쿠키에 추가하는 메서드
    public static void addTokenToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("mazayotoken", token); // "mazayotoken" 이름으로 JWT 저장
        cookie.setHttpOnly(true); // JavaScript에서 접근 불가능 (XSS 방지)
        cookie.setSecure(true);   // HTTPS에서만 전송
        cookie.setPath("/");      // 모든 경로에서 쿠키 유효
        cookie.setMaxAge(60 * 60 * 24); // 쿠키 만료 시간: 1일 (단위: 초)
        response.addCookie(cookie); // 클라이언트에 쿠키 추가
    }

    // 쿠키에서 JWT를 삭제하는 메서드 (로그아웃 처리)
    public static void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("mazayotoken", null); // "mazayotoken" 이름으로 빈 값 설정
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료
        response.addCookie(cookie); // 클라이언트에 쿠키 추가 (빈 값으로 대체)
    }
}
