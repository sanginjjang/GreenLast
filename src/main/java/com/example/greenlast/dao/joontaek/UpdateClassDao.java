package com.example.greenlast.dao.joontaek;


import com.example.greenlast.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UpdateClassDao {

    public ClassDTO getClassInfo(int classId);
    public FileDTO getThumbnailImg(int fileNo);
    public int updateClassInfo(ClassDTO classDTO);

    public List<SectionDTO> getSections(int classId);

    public List<Long> getOriSectionId();
    public List<Long> getOriLessonId();




    // 02/20 새로운 로직@@@@@@@@@@
    public List<Long> getSectionIdByClassId(int classId);

    public int deleteAllSectionsByClassId(int classId);
    public int deleteAllLessonsBySectionId(Long sectionId);

    public int insertOriSection(Long sectionId,int classId, String sectionTitle);
    public int insertNewSection(int classId, String sectionTitle);



    public Long getMaxSectionId();

    public int insertLesson(Long lessonId,Long sectionId, String lessonTitle,int fileNo);
    public int getMaxFileNo();
    public int insertNewLesson(Long sectionId, String lessonTitle, int fileNo);

    List<ClassSectionDTO> getSectionByClassId(int classId);
    List<ClassLessonDTO> getLessonsBySectionId(int classId);




    public List<LessonDTO> getLessonInfoByLessonId(Long lessonId);





}
