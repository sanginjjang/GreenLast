package com.example.greenlast.dao.sangin;

import com.example.greenlast.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created on 2025-01-23 by 한상인
 */
@Mapper
public interface RegistUserDao {
    public void registUser(UserDTO user);
    public Boolean checkUserId(String userId);
}
