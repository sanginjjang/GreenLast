package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class ClassSectionDTO {
    private int sectionId;
    private int classId;
    private String classTitle;
    private String classPermit;
}
