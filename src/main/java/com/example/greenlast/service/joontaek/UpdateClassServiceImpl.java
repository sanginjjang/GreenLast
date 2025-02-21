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
    public List<Long> getSectionIdByClassId(int classId) {
        List<Long> sectionIdList = dao.getSectionIdByClassId(classId);
        return sectionIdList;
    }

    @Override
    public int deleteAllSectionsByClassId(int classId) {
        int result = dao.deleteAllSectionsByClassId(classId);
        return result;
    }

    @Override
    public int deleteAllLessonsBySectionId(Long sectionId) {
        int result = dao.deleteAllLessonsBySectionId(sectionId);
        return result;
    }

    @Override
    public int insertOriSection(Long sectionId,int classId, String sectionTitle) {
        int result = dao.insertOriSection(sectionId,classId, sectionTitle);
        return result;
    }

    @Override
    public int insertNewSection(int classId, String sectionTitle) {
        int result = dao.insertNewSection(classId, sectionTitle);
        return result;
    }

    @Override
    public Long getMaxSectionId() {
        Long maxSectionId = dao.getMaxSectionId();
        return maxSectionId;
    }

    @Override
    public int insertLesson(Long lessonId,Long sectionId, String lessonTitle,int fileNo) {
        int result = dao.insertLesson(lessonId,sectionId, lessonTitle,fileNo);
        return result;
    }

    @Override
    public int getMaxFileNo() {
        int fileNo = dao.getMaxFileNo();
        return fileNo;
    }

    @Override
    public int insertNewLesson(Long sectionId, String lessonTitle, int fileNo) {
        int result = dao.insertNewLesson(sectionId, lessonTitle, fileNo);
        return result;
    }

    @Override
    public List<Long> getOriSectionId() {
        return List.of();
    }

    @Override
    public List<Long> getOriLessonId() {
        return List.of();
    }

    @Override
    public List<LessonDTO> getLessonInfoByLessonId(Long lessonId) {
        List<LessonDTO> lessonInfos = dao.getLessonInfoByLessonId(lessonId);
        return lessonInfos;
    }

    @Override
    public List<IntroduceDTO> getIntroduceInfo(int classId) {
        List<IntroduceDTO> introduceInfoList = dao.getIntroduceInfo(classId);
        return introduceInfoList;
    }

}





