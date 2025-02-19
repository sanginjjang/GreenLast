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

    List<ClassSectionDTO> getSectionByClassId(int classId);
    List<ClassLessonDTO> getLessonsBySectionId(int classId);
}
