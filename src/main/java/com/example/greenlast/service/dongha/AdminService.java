package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.IAdminDao;
import com.example.greenlast.dto.*;
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
    public List<ClassDTO> getPendingClasses() {
        return adminDao.findPendingClasses();
    }

    // ✅ 강의 승인 (class_permit을 'y'로 변경)
    public void approveClass(int classId) {
        adminDao.updateClassPermit(classId, "y");
    }

    // ✅ 강의 반려 (class_permit을 'n' 유지, 반려 메시지 저장 가능)
    public void rejectClass(int classId, String rejectMessage) {
        adminDao.updateClassRejection(classId, rejectMessage);
    }

}
