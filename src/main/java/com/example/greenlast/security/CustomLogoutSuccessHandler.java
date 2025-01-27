package com.example.greenlast.security;

/**
 * Created on 2025-01-27 by 한상인
 */
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 쿠키 삭제 로직
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookie.setValue(null); // 쿠키 값 삭제
                cookie.setMaxAge(0);  // 쿠키 즉시 만료
                cookie.setPath("/");  // 쿠키 경로 설정
                response.addCookie(cookie); // 삭제된 쿠키를 응답에 추가
            }
        }

        // 로그아웃 후 리다이렉트
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Location", "/view/loginForm"); // 리다이렉트 경로
    }
}
