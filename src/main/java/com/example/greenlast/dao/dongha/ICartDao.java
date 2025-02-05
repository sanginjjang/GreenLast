package com.example.greenlast.dao.dongha;

import com.example.greenlast.dto.CartDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.example.greenlast.dao.dongha
 * fileName       : ICartDao
 * author         : 이동하
 * date           : 25. 2. 2.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 2.        이동하       최초 생성
 */
@Mapper
public interface ICartDao {
    void addCart(@Param("userId") int userId, @Param("classId") int classId);
    List<CartDTO> getCartItems(@Param("userId") int userId);
    void deleteCart(@Param("cartId") int cartId);
}
