package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class ClassReviewDTO {
    private int reviewId;
    private String userId;
    private int classId;
    private double rating;
    private String content;
    private String createdAt;
    private String fileUrl;
    private String name;
}
