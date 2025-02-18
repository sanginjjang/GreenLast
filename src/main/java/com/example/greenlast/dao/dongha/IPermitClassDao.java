package com.example.greenlast.dao.dongha;

import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.ClassLessonDTO;
import com.example.greenlast.dto.ClassSectionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.example.greenlast.dao.dongha
 * fileName       : IPermitClassDao
 * author         : 이동하
 * date           : 25. 2. 14.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 14.        이동하       최초 생성
 */
@Mapper
public interface IPermitClassDao {
    List<ClassDTO> getPendingClasses();
    int approveClass(int classId);
    int rejectClass(int classId);
    ClassDTO getClassById(int classId);
    List<ClassSectionDTO> getSectionByClassId(int classId);
    List<ClassLessonDTO> getLessonsBySectionId(int classId);
}
