package com.example.greenlast.service.sangin;

import com.example.greenlast.dao.sangin.ClassDetailDao_sangin;
import com.example.greenlast.dto.ClassIntroduceDTO;
import com.example.greenlast.dto.ClassReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2025-02-10 by 한상인
 */
@Service
public class ClassDetailService_sangin {
    @Autowired
    ClassDetailDao_sangin classDetailDao;

    public List<ClassReviewDTO> getReviewsByClassId(Integer classId) {
        return classDetailDao.getReviewsByClassId(classId);
    }

    public List<ClassIntroduceDTO> getIntroducesByClassId(Integer classId) {
        return classDetailDao.getIntroducesByClassId(classId);
    }

    public int postReview(ClassReviewDTO classReviewDTO) {
        classDetailDao.postReviewStatus(classReviewDTO);
        return classDetailDao.postReview(classReviewDTO);
    }
}
