package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class UserActivityDTO {
    private int activityId;
    private int userId;
    private Integer targetId;
    private String activityType;
    private String activityDate;
}
