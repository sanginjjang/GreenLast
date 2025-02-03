package com.example.greenlast.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public WebConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor(jwtTokenProvider, "mazayotoken"); // 쿠키 이름을 전달
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor())
                .addPathPatterns("/**")  // 모든 요청에 대해 인터셉터 적용
                .excludePathPatterns("/view/loginForm", "/login", "/logout", "/css/**", "/js/**", "/images/**"); // 로그인 관련 경로는 제외
    }
}
