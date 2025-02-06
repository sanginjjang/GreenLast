package com.example.greenlast.dao.dongha;

import com.example.greenlast.dto.UserPaymentHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * packageName    : com.example.greenlast.dao.dongha
 * fileName       : IPaymentDao
 * author         : 이동하
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        이동하       최초 생성
 */
@Mapper
public interface IPaymentDao {
    void insertPayment(UserPaymentHistoryDTO userPaymentHistoryDTO);
    List<UserPaymentHistoryDTO> getUserPaymentHistory(@Param("userId") String userId);
}