package com.example.greenlast.dao.dongha;

import com.example.greenlast.dto.ClassDetailDTO;
import com.example.greenlast.dto.ClassMainDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * packageName    : com.example.greenlast.dao.dongha
 * fileName       : IClassMainDao
 * author         : 이동하
 * date           : 25. 1. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 25.        이동하       최초 생성
 */
@Mapper
public interface IClassMainDao {
    List<ClassMainDTO> findClassMain();
    List<ClassMainDTO> findClassMainByKeyword(@Param("keyword") String keyword);
    ClassDetailDTO findClassDetail(@Param("id") Long id);
    String getClassTitleById(@Param("classId") int classId);
    List<ClassMainDTO> selectAllClasses();
    List<ClassMainDTO> selectClassesByCategory(@Param("category") String category);
}
