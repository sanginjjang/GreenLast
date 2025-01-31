package com.example.greenlast.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName    : com.example.greenlast.config
 * fileName       : WebConfig
 * author         : 이동하
 * date           : 25. 1. 30.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 30.        이동하       최초 생성
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:\\Users\\siran\\Documents\\intelliJ\\GreenLast\\src\\main\\resources\\static\\images");
    }
}
