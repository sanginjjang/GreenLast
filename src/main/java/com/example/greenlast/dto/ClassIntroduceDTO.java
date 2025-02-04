package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class ClassIntroduceDTO {
    private int introduceId;
    private int classId;
    private String introText;
    private String fileNo;
    private String fileUrl;
}
