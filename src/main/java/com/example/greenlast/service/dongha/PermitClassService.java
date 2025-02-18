package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.IPermitClassDao;
import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.ClassLessonDTO;
import com.example.greenlast.dto.ClassSectionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.greenlast.service.dongha
 * fileName       : PermitClassService
 * author         : 이동하
 * date           : 25. 2. 14.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 14.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class PermitClassService {
    private final IPermitClassDao permitClassDao;

    public List<ClassDTO> getPendingClasses() {
        return permitClassDao.getPendingClasses();
    }
    public int approveClass(int classId) {
        return permitClassDao.approveClass(classId);
    }

    public int rejectClass(int classId) {
        return permitClassDao.rejectClass(classId);
    }

    public ClassDTO getClassDetail(int classId) {
        return permitClassDao.getClassById(classId);
    }

    public List<ClassSectionDTO> getClassCurriculum(int classId) {
        System.out.println(classId);
        System.out.println(classId);
        System.out.println(classId);
        List<ClassSectionDTO> sections = permitClassDao.getSectionByClassId(classId);
        System.out.println(sections);
        System.out.println(sections);
        System.out.println(sections);
        System.out.println(sections);
        for (ClassSectionDTO section : sections) {
            List<ClassLessonDTO> lessonDTOList = permitClassDao.getLessonsBySectionId(section.getSectionId());
            System.out.println(section.getSectionId());
            System.out.println(section.getSectionId());
            System.out.println(section.getSectionId());
            //section.setLessons(lessons);
            section.setLessonDTOList(lessonDTOList);
        }

        System.out.println("📌 최종 curriculum 데이터: " + sections);
        return sections;
    }
}
