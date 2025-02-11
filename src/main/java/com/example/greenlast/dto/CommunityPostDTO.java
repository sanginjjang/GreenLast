package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class CommunityPostDTO {
    private int postId;
    private Integer classId;
    private String userId;
    private String title;
    private String content;
    private String createdAt;
    private String category;
    private int views;
    private int commentCount;
    private String pageType;
}
