package com.example.greenlast.service.joontaek;


import com.example.greenlast.dao.joontaek.UpdateClassDao;
import com.example.greenlast.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateClassServiceImpl implements UpdateClassService {

    @Autowired
    UpdateClassDao dao;

    @Override
    public ClassDTO getClassInfo(int classId) {

        ClassDTO classInfo = dao.getClassInfo(classId);

        return classInfo;
    }

    @Override
    public FileDTO getThumbnailImg(int fileNo) {
        FileDTO fileInfo = dao.getThumbnailImg(fileNo);
        return fileInfo;
    }

    @Override
    public int updateClassInfo(ClassDTO classDTO) {
        int result = dao.updateClassInfo(classDTO);
        return result;
    }

    @Override
    public List<ClassSectionDTO> getClassCurriculum(int classId) {

        List<ClassSectionDTO> sections = dao.getSectionByClassId(classId);

        for (ClassSectionDTO section : sections) {
            List<ClassLessonDTO> lessonDTOList = dao.getLessonsBySectionId(section.getSectionId());
            section.setLessonDTOList(lessonDTOList);
        }

        System.out.println("📌 최종 curriculum 데이터: " + sections);
        return sections;
    }

    @Override
    public List<SectionDTO> getSections(int classId) {
        List<SectionDTO> sections = dao.getSections(classId);
        return sections;
    }

    @Override
    public List<Long> getOriSectionId() {

        List<Long> sectionIdList = dao.getOriSectionId();
        return sectionIdList;

    }

    @Override
    public List<Long> getOriLessonId() {
        List<Long> lessonIdList = dao.getOriLessonId();
        return lessonIdList;
    }
}

