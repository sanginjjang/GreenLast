package com.example.greenlast.dao.sangin;

import com.example.greenlast.security.CustomUserDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao_sangin {
    // 사용자 정보를 username으로 조회하는 SQL 쿼리 매핑
    CustomUserDetails findByUserId(String userId);
}
