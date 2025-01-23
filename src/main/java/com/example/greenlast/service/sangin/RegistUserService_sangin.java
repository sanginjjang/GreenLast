package com.example.greenlast.service.sangin;

import com.example.greenlast.dao.sangin.RegistUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2025-01-23 by 한상인
 */
@Service
public class RegistUserService_sangin {
    @Autowired
    RegistUserDao registUserDao;

    public boolean checkUserId(String userId) {
        System.out.println("service/registUserService/checkUserId: " + userId);

        // DAO에서 값이 null일 경우 false 반환
        Boolean isDuplicate = registUserDao.checkUserId(userId); // Boolean으로 선언
        return isDuplicate != null ? isDuplicate : false;
    }
}
