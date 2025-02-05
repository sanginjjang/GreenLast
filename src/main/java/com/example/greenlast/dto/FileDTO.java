package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class FileDTO {
    private int fileNo;
    private String fileType;
    private String fileRefNo;
    private String fileOldName;
    private String fileNewName;
    private String fileExt;
    private int fileSize;
    private String fileUrl;
    private int fileSeq;
}
