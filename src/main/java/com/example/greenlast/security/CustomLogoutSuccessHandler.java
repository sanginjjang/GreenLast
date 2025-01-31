package com.example.greenlast.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("로그아웃 핸들러 작동!!");
        // 쿠키 삭제 로직
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookie.setValue(null); // 쿠키 값 삭제
                cookie.setMaxAge(0);  // 쿠키 즉시 만료
                cookie.setPath("/");  // 쿠키 경로 설정
                response.addCookie(cookie); // 삭제된 쿠키를 응답에 추가
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);

        try {
            response.sendRedirect("/view/mypage/infoForm"); // 리다이렉트 경로 // 나중에 수정 예정
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }
}
