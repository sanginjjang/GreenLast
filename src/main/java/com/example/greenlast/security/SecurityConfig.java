package com.example.greenlast.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Spring Security 설정 클래스
@Configuration
@EnableWebSecurity // Spring Security를 활성화
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    // JwtTokenProvider를 주입받아 필터에 사용
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/view/loginForm", "/login", "/logout", "/css/**", "/static/**", "/static/**", "/mapper/**", "/fonts/**", "/images/**", "/js/**").permitAll()
//                                .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin(form -> form // 기본 로그인 폼 활성화
//                        .loginPage("/view/loginForm")
                        .defaultSuccessUrl("/")
                        .permitAll() // 로그인 페이지 접근 허용
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, "mazayotoken"),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
