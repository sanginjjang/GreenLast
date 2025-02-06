package com.example.greenlast.dao.sangin;

import com.example.greenlast.dto.UserDTO;
import com.example.greenlast.security.CustomUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao_sangin {
    // 사용자 정보를 username으로 조회하는 SQL 쿼리 매핑
    CustomUserDetails findByUserId(String userId);

    UserDTO findByEmail(String email);

    void insertUser(UserDTO user);

    // ✅ 아이디 찾기: 이름과 이메일로 사용자 존재 여부 확인
    boolean existsByNameAndEmail(@Param("name") String name, @Param("email") String email);

    // ✅ 아이디 찾기: 이름과 이메일로 사용자 정보 조회
    UserDTO getUserByNameAndEmail(@Param("name") String name, @Param("email") String email);

    // ✅ 비밀번호 찾기: 아이디, 이름, 이메일로 사용자 존재 여부 확인
    boolean existsByIdAndEmail(@Param("userId") String userId, @Param("name") String name, @Param("email") String email);

    // ✅ 비밀번호 찾기: 아이디, 이름, 이메일로 사용자 정보 조회
    UserDTO getUserByIdAndEmail(@Param("userId") String userId, @Param("name") String name, @Param("email") String email);

    boolean updatePassword(@Param("userId") String userId, @Param("password") String password);

}
