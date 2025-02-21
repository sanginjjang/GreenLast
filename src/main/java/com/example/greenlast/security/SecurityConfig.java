package com.example.greenlast.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS 설정 적용
                .csrf(csrf -> csrf.disable()) // ✅ CSRF 비활성화
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // ✅ Iframe 허용
                        .contentSecurityPolicy(csp -> csp.policyDirectives("frame-src *; script-src * 'unsafe-inline' 'unsafe-eval'; style-src * 'unsafe-inline';")) // ✅ CSP 정책 적용
                )
                .authorizeHttpRequests(auth -> auth
                        // 🔹 업로드된 파일들 (`/uploads/**`) 최우선 허용
                        .requestMatchers(
                                "/uploads/**",
                                "/C:/upload-dir/uploads" // 만약 윈도우 경로 그대로 써야 한다면 허용
                        ).permitAll()

                        // 🔹 인증 없이 접근 가능한 경로들
                        .requestMatchers(
                                "/",
                                "/view/loginForm",
                                "/view/registUserForm",
                                "/view/findIdByPhoneForm",
                                "/view/findIdByEmailForm",
                                "/view/findPwByPhoneForm",
                                "/view/findPwByEmailForm",
                                "/login",
                                "/logout",
                                "/api/mypage/getUserById",
                                "/api/users/**",
                                "/css/**",
                                "/static/**",
                                "/mapper/**",
                                "/fonts/**",
                                "/images/**",
                                "/js/**",
                                "/api/file/upload"
                                //"/**" //나중에 이거 주석 처리 하시오
                        ).permitAll()

                        // 🔹 **ADMIN 전용 경로 ("/admin/**")**
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 🔹 PICLE 접근 차단 (USER, ADMIN만 허용)
                        .requestMatchers("/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/view/loginForm")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/sangin/test/1")
                        .successHandler(new CustomAuthenticationSuccessHandler(jwtTokenProvider))
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .permitAll()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, "mazayotoken"),
                        UsernamePasswordAuthenticationFilter.class
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ 허용할 도메인 명확히 지정 (192.168.0.2:8080 추가)
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8080",
                "http://192.168.0.2:8080"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        // ✅ credentials 허용 (쿠키 사용 가능)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
