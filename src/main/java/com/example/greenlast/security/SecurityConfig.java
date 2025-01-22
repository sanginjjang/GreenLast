package com.example.greenlast.security;

// 보안 설정(SecurityConfig)을 담당합니다.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 이 클래스가 설정 파일임을 Spring에 알립니다.
@EnableWebSecurity // Spring Security를 활성화합니다.
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    // JwtTokenProvider를 가져와서 사용할 준비를 합니다.
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // CSRF 방지 비활성화
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/*/**").permitAll()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form // 기본 로그인 폼 활성화
//                        .loginPage("/login") // 커스텀 로그인 페이지 경로 (생략 시 기본 페이지 사용)
                        .permitAll() // 로그인 페이지 접근 허용
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

}
