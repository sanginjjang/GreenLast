package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClassDTO {
    private int classId;
    private String userId;
    private String classTitle;
    private String classCapsule;
    private String classSubtitle;
    private String description;
    private int classPrice;
    private String classCategory;
    private String classCreateDate;
    private String classPermit;
    private String classRecom;
    private String classLevel;
    private int fileNo;
    private MultipartFile thumbnail;

}
