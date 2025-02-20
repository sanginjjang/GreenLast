package com.example.greenlast.dao.dongha;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.example.greenlast.dao.dongha
 * fileName       : UserDao_dong
 * author         : 이동하
 * date           : 25. 2. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 20.        이동하       최초 생성
 */
@Mapper
public interface UserDao_dong {
    int updateUserRole(@Param("userId") String userId, @Param("newRole") String newRole);
    int deleteUser(String userId);
}
