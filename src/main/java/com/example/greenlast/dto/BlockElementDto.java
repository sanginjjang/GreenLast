package com.example.greenlast.dto;

import lombok.Data;

/**
 * Created on 2025-02-18 by 한상인
 */
@Data
public class BlockElementDto {
    private int elementId;
    private int blockId;
    private String elementType;
    //text image
    private String elementContent;

    private String blockType;
}
