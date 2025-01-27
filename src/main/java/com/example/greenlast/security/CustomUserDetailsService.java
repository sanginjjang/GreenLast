package com.example.greenlast.security;

import com.example.greenlast.dao.sangin.UserDao_sangin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
//    나중에 AuthenticationManager가 customUserDetailsService 호출해서 CustomUserDetails 반환하면
//    DaoAuthenticationProvider에서 UsernamePasswordAuthenticationFilter(사용자 로그인 입력 정보 = 아이디 비번)이랑
//    matches 함수 써서 비밀번호 비교함. 아이디는 여기서 findByUserId사용해서 일단 db에서 갖고옴
//    그리고 스프링 di가 관리하는 영역에 들어간 BCryptEncoderPassword 가 서로 복호화하고 암호해제해서 비교함.
//    로그인 성공시 쿠키발급하고 jwt필터가 필터 검증 => 쿠키있으면 security config에 의해 역할에 따른 요청 가능해지고
//    로그인 실패시 로그인 폼으로 이동하게 만듬

    private final UserDao_sangin userDao;

    // UserDao_sangin 주입
    public CustomUserDetailsService(UserDao_sangin userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // MyBatis DAO를 통해 사용자 정보 조회
        CustomUserDetails userDetails = userDao.findByUserId(userId);

        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found: " + userId);
        }

        return userDetails;
    }
}
