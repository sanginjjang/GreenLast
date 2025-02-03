package com.example.greenlast.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
        System.out.println("시큐리티 필터 체인 작동~~");
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/view/loginForm", "/login", "/logout", "/css/**", "/static/**", "/mapper/**", "/fonts/**", "/images/**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/view/loginForm")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/sangin/test/1") // 성공 후 리다이렉트 URL
                        .successHandler(new CustomAuthenticationSuccessHandler(jwtTokenProvider)) // 커스텀 성공 핸들러 등록
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler()) // 커스텀 로그아웃 성공 핸들러
                        .permitAll()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, "mazayotoken"),
                        UsernamePasswordAuthenticationFilter.class
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 Stateless로 설정
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
