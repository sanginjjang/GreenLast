package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class CommunityCommentDTO {
    private int commentId;
    private int postId;
    private String userId;
    private String content;
    private String createdAt;
}
