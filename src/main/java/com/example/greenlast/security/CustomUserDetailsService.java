package com.example.greenlast.security;

/**
 * Created on 2025-01-22 by 한상인
 *
 * 이 클래스는 Spring Security에서 사용자 정보를 불러오는 데 사용됩니다.
 * 사용자 이름(username)을 기반으로 데이터베이스에서 정보를 찾아와,
 * Spring Security에서 사용할 수 있는 형태(UserDetails)로 반환합니다.
 */

import org.springframework.security.core.userdetails.UserDetails; // Spring Security에서 사용자 정보를 다루는 인터페이스
import org.springframework.security.core.userdetails.UserDetailsService; // 사용자 정보를 불러오는 인터페이스
import org.springframework.security.core.userdetails.UsernameNotFoundException; // 사용자가 없을 때 발생하는 예외
import org.springframework.stereotype.Service; // 이 클래스가 서비스 레이어임을 Spring에 알림

@Service // Spring에서 이 클래스를 서비스로 관리합니다.
public class CustomUserDetailsService implements UserDetailsService {

    // 사용자 이름(username)을 기반으로 사용자 정보를 불러오는 메서드입니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기에 실제로 데이터베이스에서 사용자 정보를 조회하는 로직을 작성해야 합니다.

        // 예시: username이 "user"라면 사용자 정보를 반환합니다.
        if ("user".equals(username)) {
            // "user"라는 이름의 사용자 정보를 반환합니다.
            return new CustomUserDetails("user", "password", "USER"); // 사용자 이름, 비밀번호, 역할을 포함
        } else {
            // username이 "user"가 아니라면 예외를 발생시킵니다.
            throw new UsernameNotFoundException("User not found"); // 사용자 정보를 찾을 수 없다는 메시지와 함께 예외를 던짐
        }
    }
}
