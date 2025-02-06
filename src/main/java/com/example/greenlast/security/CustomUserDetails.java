package com.example.greenlast.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final String userId;
    private final String password;
    private final String role; // ✅ 역할 추가

    public CustomUserDetails(String userId, String password, String role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("🔹 CustomUserDetails - getAuthorities() 호출됨 | role = " + role);
        return Collections.singletonList(new SimpleGrantedAuthority(role)); // ✅ GrantedAuthority 형태로 반환
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
