package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class ScheduleDTO {
    private int scheduleId;
    private int userId;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
}
