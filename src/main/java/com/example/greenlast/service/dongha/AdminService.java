package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.IAdminDao;
import com.example.greenlast.dto.AgeGroupDTO;
import com.example.greenlast.dto.DailyUserDTO;
import com.example.greenlast.dto.GenderDTO;
import com.example.greenlast.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.greenlast.service.dongha
 * fileName       : AdminService
 * author         : 이동하
 * date           : 25. 1. 23.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 23.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class AdminService {
    private final IAdminDao adminDao;

    public List<DailyUserDTO> getDailyUsers() {
        return adminDao.findDailyUser();
    }
    public List<AgeGroupDTO> getAgeGroups() {
        return adminDao.findAgeGroup();
    }
    public List<GenderDTO> getGenders() {
        return adminDao.findGender();
    }
    public List<UserDTO> getUsers() {
        return adminDao.findUser();
    }
}
