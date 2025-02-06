package com.example.greenlast.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 인증 필터: 모든 요청에서 JWT를 검증하고 인증 정보를 SecurityContext에 설정
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final String jwtCookieName; // 쿠키 이름을 외부 설정에서 가져오도록 수정

    // JwtTokenProvider와 쿠키 이름을 주입받아 초기화
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, String jwtCookieName) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtCookieName = jwtCookieName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 요청에서 JWT 토큰을 추출
            System.out.println("필터 토큰 추출 실시");
            String token = resolveToken(request);
            System.out.println("토큰: " + token);

            // 토큰이 유효하다면 사용자 정보를 설정
            if (token != null && jwtTokenProvider.validateToken(token)) {
                String userId = jwtTokenProvider.getUserIdFromToken(token); // 토큰에서 사용자 이름 추출
                String role = jwtTokenProvider.getRoleFromToken(token); // 토큰에서 역할 추출

                System.out.println("jwtAuthenticationFilter: " + userId);
                System.out.println("jwtAuthenticationFilter: " + role);

                // 사용자 정보를 담은 CustomUserDetails 객체 생성
                CustomUserDetails userDetails = new CustomUserDetails(userId,null, role);

                // ✅ Spring Security에 Authentication 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // ✅ SecurityContextHolder에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("🟢 SecurityContextHolder - 인증 저장 완료");
            }
        } catch (Exception e) {
            // JWT 검증 중 발생한 예외 처리 (필요 시 로그 출력)
            logger.error("JWT 인증 중 오류 발생: " + e.getMessage());
        }

        // 다음 필터로 요청을 넘김
        filterChain.doFilter(request, response);
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
