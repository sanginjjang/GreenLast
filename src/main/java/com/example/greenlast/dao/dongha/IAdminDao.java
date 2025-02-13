package com.example.greenlast.dao.dongha;

import com.example.greenlast.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

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
    List<ClassDTO> getPendingClasses(); // 대기 중인 강의 목록 가져오기
    int updateClassPermit(@Param("classId") int classId, @Param("permit") String permit); // 승인 처리
    int insertRejectMessage(@Param("classId") int classId, @Param("message") String message); // 반려 메시지 저장
}
