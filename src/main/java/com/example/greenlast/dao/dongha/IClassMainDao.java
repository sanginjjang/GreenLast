package com.example.greenlast.dao.dongha;

import com.example.greenlast.dto.ClassDTO;
import org.apache.ibatis.annotations.Mapper;

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
    List<ClassDTO> findClassMain();
}
