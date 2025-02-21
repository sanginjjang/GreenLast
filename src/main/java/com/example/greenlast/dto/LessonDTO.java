package com.example.greenlast.dto;

import lombok.Data;

@Data
public class LessonDTO {

    private Long lessonId;
    private Long sectionId;
    private String lessonTitle;
    private int fileNo;
}
