package com.example.greenlast.security;

/**
 * Created on 2025-01-22 by 한상인
 *
 * 이 클래스는 모든 요청(Request)이 들어올 때마다 실행되는 JWT 인증 필터입니다.
 * 요청에 포함된 JWT 토큰을 검사하고, 유효하다면 사용자 정보를 Spring Security에 등록합니다.
 */

import jakarta.servlet.FilterChain; // 필터 체인을 제어하기 위해 사용
import jakarta.servlet.ServletException; // 서블릿 관련 예외 처리
import jakarta.servlet.http.HttpServletRequest; // 클라이언트 요청 정보를 처리
import jakarta.servlet.http.HttpServletResponse; // 클라이언트 응답 정보를 처리
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // 인증 객체
import org.springframework.security.core.context.SecurityContextHolder; // Security 컨텍스트에 인증 정보를 저장
import org.springframework.web.filter.OncePerRequestFilter; // 요청마다 한 번만 실행되는 필터

import java.io.IOException; // 입출력 예외 처리
//어쏀틱케이션은 누구인가? 어떠라이제이션은 무엇을 할 수 있는가?당신이 이것을 해도 되는 사람인가?묻는 단계
//신원확인 후 권한확인
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // JwtTokenProvider를 주입받아 JWT 토큰 검증과 정보를 추출하는 데 사용합니다.
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청에서 JWT 토큰을 추출합니다.
        String token = resolveToken(request);

        // 토큰이 존재하고 유효하다면, 사용자 정보를 SecurityContext에 저장합니다.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token); // 토큰에서 사용자 이름 추출
            String role = jwtTokenProvider.getRole(token); // 토큰에서 사용자 역할 추출

            // 사용자 정보를 담은 CustomUserDetails 객체를 생성합니다.
            CustomUserDetails userDetails = new CustomUserDetails(username, null, role);

            // Spring Security에 사용자 인증 정보를 설정합니다.
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        }

        // 다음 필터로 요청을 넘깁니다.
        filterChain.doFilter(request, response);
    }

    // 요청의 헤더에서 JWT 토큰을 추출하는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Authorization 헤더에서 토큰 가져오기
        // 토큰이 "Bearer "로 시작하면, 앞의 "Bearer " 부분을 제거하고 반환
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }
}
