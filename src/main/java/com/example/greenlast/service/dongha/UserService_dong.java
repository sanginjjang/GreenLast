package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.UserDao_dong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.example.greenlast.service.dongha
 * fileName       : UserService_dong
 * author         : 이동하
 * date           : 25. 2. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 20.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class UserService_dong {
    private final UserDao_dong userDao;

    public boolean updateUserRole(String userId, String newRole) {
        int updatedRows = userDao.updateUserRole(userId, newRole);
        return updatedRows > 0;
    }

    public boolean deleteUser(String userId) {
        int deleteRows = userDao.deleteUser(userId);
        return deleteRows > 0;
    }
}
