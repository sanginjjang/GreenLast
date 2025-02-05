package com.example.greenlast.dto;

import lombok.Data;

/**
 * packageName    : com.example.greenlast.dto
 * fileName       : ClassDetailDTO
 * author         : 이동하
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        이동하       최초 생성
 */
@Data
public class ClassDetailDTO {
    private Long classId;//
    private String title;//
    private String instructor;//
    private int price;//
    private String fileNo;//
    private String description;//
    private String classCapsule;
    private String classSubtitle;
    private String classCategory;
    private String classRecom;
    private String classLevel;
}
