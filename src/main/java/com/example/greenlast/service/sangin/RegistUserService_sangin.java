package com.example.greenlast.service.sangin;

import com.example.greenlast.dao.sangin.RegistUserDao;
import com.example.greenlast.dto.UserDTO;
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
        Boolean isDuplicate = registUserDao.checkUserId(userId); // Boolean으로 선언
        return isDuplicate != null ? isDuplicate : false;
    }

    public boolean checkUserEmail(String email) {
        System.out.println("service/registUserService/checkUserEmail: " + email);
        Boolean isDuplicate = registUserDao.checkUserEmail(email); // Boolean으로 선언
        return isDuplicate != null ? isDuplicate : false;
    }

    public boolean checkUserPhoneNumber(String phoneNumber) {
        System.out.println("service/registUserService/checkUserPhoneNUmber : " + phoneNumber);
        Boolean isDuplicate = registUserDao.checkUserPhoneNumber(phoneNumber); // Boolean으로 선언
        return isDuplicate != null ? isDuplicate : false;
    }

    public int registUser(UserDTO user) {
        int result = registUserDao.registUser(user);
        return result;
    }
}
