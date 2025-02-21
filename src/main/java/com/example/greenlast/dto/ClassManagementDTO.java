package com.example.greenlast.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ClassManagementDTO {


    private int classId;
    private MultipartFile thumbnail;
    private String classTitle;
    private double rating;
    private int allCntStudent;
    private int allRevenue;
    private String classPermit;
}
