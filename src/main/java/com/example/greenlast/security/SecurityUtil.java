package com.example.greenlast.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SecurityUtil {

    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println("SecurityUtil UserID: " + userDetails.getUserId());  // ✅ 디버깅 추가
            return userDetails.getUserId();
        }
        return null;
    }

    public static String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            System.out.println("SecurityUtil - Authentication 객체 확인: " + authentication);

            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                System.out.println("SecurityUtil - UserDetails에서 Role 가져오기: " + userDetails.getRole());
                return userDetails.getRole();
            }

            // 🔥 직접 GrantedAuthority에서 역할 가져오기
            if (authentication.getAuthorities() != null) {
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    System.out.println("SecurityUtil - GrantedAuthority에서 Role 가져오기: " + authority.getAuthority());
                    return authority.getAuthority();
                }
            }
        }
        return null;
    }

}
