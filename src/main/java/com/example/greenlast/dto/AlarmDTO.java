package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class AlarmDTO {
    private int alarmId;
    private String userId;
    private String alarmContent;
    private boolean readStatus;
    private String alarmDate;
    private String alarmUrl;
}
