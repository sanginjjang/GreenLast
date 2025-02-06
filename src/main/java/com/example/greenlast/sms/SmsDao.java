package com.example.greenlast.sms;

/**
 * Created on 2025-02-06 by 한상인
 */

import com.example.greenlast.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsDao {

    // ✅ 이름과 휴대폰 번호로 사용자 확인
    boolean existsByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    // ✅ 인증 성공 시 사용자 정보 조회
    UserDTO getUserByNameAndPhone(@Param("name") String name, @Param("phone") String phone);
}
