package com.example.greenlast.security;

import com.example.greenlast.dao.sangin.UserDao_sangin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

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
