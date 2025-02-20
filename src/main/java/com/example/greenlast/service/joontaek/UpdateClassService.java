package com.example.greenlast.service.joontaek;

import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.ClassSectionDTO;
import com.example.greenlast.dto.FileDTO;
import com.example.greenlast.dto.SectionDTO;

import java.util.List;

public interface UpdateClassService {


    public ClassDTO getClassInfo(int classId);
    public FileDTO getThumbnailImg(int fileNo);
    public int updateClassInfo(ClassDTO classDTO);
    public List<ClassSectionDTO> getClassCurriculum(int classId);

    public List<SectionDTO> getSections(int classId);




    public int updateSection(String sectionTitle, Long sectionId);
    public int updateNewSection(int classId, String sectionTitle);

    public int updateLesson(String lessonTitle, Long lessonId);
    public int updateNewLesson(Long sectionId, String lessonTitle);

    public int deleteSection(Long sectionId);
    public int deleteLesson(Long lessonId);





    public List<Long> getOriSectionId();
    public List<Long> getOriLessonId();
}
