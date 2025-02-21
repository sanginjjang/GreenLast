package com.example.greenlast.service.joontaek;

import com.example.greenlast.dto.*;

import java.util.List;

public interface UpdateClassService {


    public ClassDTO getClassInfo(int classId);
    public FileDTO getThumbnailImg(int fileNo);
    public int updateClassInfo(ClassDTO classDTO);
    public List<ClassSectionDTO> getClassCurriculum(int classId);

    public List<SectionDTO> getSections(int classId);


    public List<Long> getSectionIdByClassId(int classId);

    public int deleteAllSectionsByClassId(int classId);
    public int deleteAllLessonsBySectionId(Long sectionId);


    public int insertOriSection(Long sectionId, int classId, String sectionTitle);
    public int insertNewSection(int classId, String sectionTitle);

    public Long getMaxSectionId();

    public int insertLesson(Long lessonId,Long sectionId, String lessonTitle,int fileNo);
    public int getMaxFileNo();
    public int insertNewLesson(Long sectionId, String lessonTitle, int fileNo);



    public List<Long> getOriSectionId();
    public List<Long> getOriLessonId();

    public List<LessonDTO> getLessonInfoByLessonId(Long lessonId);


}
