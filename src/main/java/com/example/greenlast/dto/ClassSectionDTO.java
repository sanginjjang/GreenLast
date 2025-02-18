package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

import java.util.List;

@Data
public class ClassSectionDTO {
    private int sectionId;
    private int classId;
    private String sectionTitle;
    private String classPermit;
    private String totalTime;

    //    레슨 객체를 담기 위합입니다
    private List<ClassLessonDTO> lessonDTOList;
}
