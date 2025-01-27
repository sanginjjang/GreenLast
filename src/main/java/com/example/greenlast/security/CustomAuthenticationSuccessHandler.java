package com.example.greenlast.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("로그인 핸들러 작동~");
        try {
            // 인증된 사용자 정보 가져오기
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // JWT 생성
            String token = jwtTokenProvider.createToken(userDetails.getUserId(), userDetails.getRole());

            // JWT를 쿠키에 추가
            CookieUtil.addTokenToCookie(response, token);

            // 로그인 성공 후 "/"로 리다이렉트
            response.sendRedirect("/"); // 원하는 URL로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
