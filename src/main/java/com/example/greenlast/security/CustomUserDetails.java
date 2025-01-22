package com.example.greenlast.security;

/**
 * Created on 2025-01-22 by 한상인
 *
 * 이 클래스는 Spring Security가 사용자 인증과 권한 관리를 위해 사용하는 `UserDetails`를 구현한 클래스입니다.
 *
 * 주요 역할:
 * - 사용자 이름, 비밀번호, 권한(Role) 정보를 제공.
 * - 계정 상태(잠김, 만료 여부 등)를 설정.
 */

import org.springframework.security.core.GrantedAuthority; // 사용자의 권한을 나타내는 인터페이스
import org.springframework.security.core.userdetails.UserDetails; // Spring Security에서 사용자 정보를 다루는 인터페이스

import java.util.Collection; // 여러 권한을 저장하는 컬렉션 타입
import java.util.Collections; // 컬렉션 관련 유틸리티 클래스

public class CustomUserDetails implements UserDetails {

    private final String username; // 사용자 이름
    private final String password; // 사용자 비밀번호
    private final String role; // 사용자 권한 (예: "USER", "ADMIN")

    // 생성자: 사용자 이름, 비밀번호, 권한 정보를 초기화합니다.
    public CustomUserDetails(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * 사용자의 권한(Role)을 반환합니다.
     * 예: "ROLE_USER", "ROLE_ADMIN"
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한은 하나만 포함하는 컬렉션으로 반환합니다.
        return Collections.singleton(() -> "ROLE_" + role);
    }

    /**
     * 사용자 비밀번호를 반환합니다.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 사용자 이름을 반환합니다.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 계정이 만료되지 않았는지 확인합니다.
     * 항상 `true`를 반환하므로 만료되지 않았다고 가정합니다.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠겨 있지 않은지 확인합니다.
     * 항상 `true`를 반환하므로 계정이 잠기지 않았다고 가정합니다.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 인증 정보(비밀번호 등)가 만료되지 않았는지 확인합니다.
     * 항상 `true`를 반환하므로 인증 정보가 만료되지 않았다고 가정합니다.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 활성화되어 있는지 확인합니다.
     * 항상 `true`를 반환하므로 계정이 활성화 상태라고 가정합니다.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
