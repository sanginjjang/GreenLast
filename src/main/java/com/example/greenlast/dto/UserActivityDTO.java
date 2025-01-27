package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class UserActivityDTO {
    private int activityId;
    private String userId;
    private Integer targetId;
    private String activityType;
    private String activityDate;
}
