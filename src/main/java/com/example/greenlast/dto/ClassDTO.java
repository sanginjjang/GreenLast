package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class ClassDTO {
    private int classId;
    private String userId;
    private String classTitle;
    private String classCapsule;
    private String classSubtitle;
    private String description;
    private double classPrice;
    private String classCategory;
    private String classCreateDate;
    private String classTerm;
    private String classPermit;
    private String classRecom;
    private String classLevel;
    private String thumbNail;
}
