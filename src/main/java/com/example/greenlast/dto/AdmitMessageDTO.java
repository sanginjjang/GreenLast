package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class AdmitMessageDTO {
    private int denyId;
    private int classId;
    private String content;
    private String denyAt;
}
