package com.example.greenlast.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// Spring Security에서 인증된 사용자 정보를 제공하는 클래스
public class CustomUserDetails implements UserDetails {

    private final String userId;     // 사용자 ID
    private final String role;       // 역할 (ROLE_USER, ROLE_ADMIN)
    private final String password;   // 사용자 비밀번호

    // 생성자
    public CustomUserDetails(String userId, String role, String password) {
        this.userId = userId;
        this.role = role;
        this.password = password;
    }

    // 사용자 ID 반환
    public String getUserId() {
        return userId;
    }

    // 사용자 역할 반환
    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 정보를 ROLE_ 접두사를 붙여서 반환
        return Collections.singleton(() -> "ROLE_" + role);
    }

    @Override
    public String getPassword() {
        return password; // 데이터베이스에 저장된 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return userId; // Spring Security는 username으로 사용자 ID를 요구
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 인증 정보 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }
}
