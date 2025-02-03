package com.example.greenlast.dto;

import lombok.Data;

/**
 * packageName    : com.example.greenlast.dto
 * fileName       : ClassMainDTO
 * author         : 이동하
 * date           : 25. 1. 27.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 27.        이동하       최초 생성
 */
@Data
public class ClassMainDTO {
    private Long classId;
    private String thumbNail;
    private String userId;
    private String classTitle;
    private String classPrice;
    private String classCapsule;
    private String classSubtitle;
    private String description;
    private String classCategory;
    private String classTerm;
    private String classPermit;
    private String classRecom;
    private String classLevel;
}
