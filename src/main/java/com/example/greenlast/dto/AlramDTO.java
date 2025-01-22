package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class AlramDTO {
    private int alramId;
    private int userId;
    private String alramContent;
    private boolean readStatus;
    private String alramDate;
}
