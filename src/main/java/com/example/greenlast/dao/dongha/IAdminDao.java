package com.example.greenlast.dao.dongha;

import com.example.greenlast.dto.AgeGroupDTO;
import com.example.greenlast.dto.DailyUserDTO;
import com.example.greenlast.dto.GenderDTO;
import com.example.greenlast.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.example.greenlast.dao.dongha
 * fileName       : IAdminDao
 * author         : 이동하
 * date           : 25. 1. 23.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 23.        이동하       최초 생성
 */
@Mapper
public interface IAdminDao {
    List<DailyUserDTO> findDailyUser();
    List<AgeGroupDTO> findAgeGroup();
    List<GenderDTO> findGender();
    List<UserDTO> findUser();
}
