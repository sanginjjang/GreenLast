package com.example.greenlast.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final String jwtCookieName;

    public UserInterceptor(JwtTokenProvider jwtTokenProvider, String jwtCookieName) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtCookieName = jwtCookieName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 쿠키에서 JWT 토큰을 가져오기
        String token = resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            // JWT가 유효하다면 사용자 정보를 SecurityContext에 설정
            String userId = jwtTokenProvider.getUserIdFromToken(token); // 토큰에서 사용자 이름 추출
            String role = jwtTokenProvider.getRoleFromToken(token); // 토큰에서 역할 추출

            // 사용자 정보를 담은 CustomUserDetails 객체 생성
            CustomUserDetails userDetails = new CustomUserDetails(userId, null, role);

            // Spring Security에 사용자 인증 정보 설정
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증된 사용자가 있으면 request에 사용자 정보를 추가합니다.
            request.setAttribute("user", userDetails);  // request에 user 저장
        }

        // 계속해서 다른 인터셉터나 핸들러가 실행되도록 true를 반환
        return true;
    }

    // 요청에서 쿠키를 통해 JWT 토큰을 추출하는 메서드
    private String resolveToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                // 설정된 쿠키 이름과 일치하는 JWT 반환
                if (jwtCookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null; // 쿠키에 JWT가 없으면 null 반환
    }
}
