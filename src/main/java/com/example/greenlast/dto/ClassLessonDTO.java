package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class ClassLessonDTO {
    private int lessonId;
    private int sectionId;
    private String lessonTitle;
    private String lessonContent;
    private String lessonFree;
}
