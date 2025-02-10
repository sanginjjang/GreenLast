package com.example.greenlast.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 쿠키를 생성, 삭제하는 유틸리티 클래스
public class CookieUtil {

    // JWT를 쿠키에 추가하는 메서드
    public static void addTokenToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("mazayotoken", token);
        cookie.setHttpOnly(true); // JavaScript에서 접근 불가능

        // ✅ HTTPS가 아닐 때 Secure 속성 해제 (테스트 환경)
        boolean isLocal = isLocalEnvironment();
        cookie.setSecure(!isLocal); // 로컬 환경에서는 Secure 설정 해제

        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 1일 (86400초)
        response.addCookie(cookie);
    }

    // ✅ 현재 환경이 로컬인지 판단하는 메서드
    private static boolean isLocalEnvironment() {
        String host = System.getenv("HOSTNAME");
        return host == null || host.contains("localhost") || host.startsWith("192.168.") || host.startsWith("127.");
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

    public static String getTokenFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("mazayotoken".equals(cookie.getName())) { // ✅ JWT 쿠키 이름
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
