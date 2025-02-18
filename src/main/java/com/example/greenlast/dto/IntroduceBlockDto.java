package com.example.greenlast.dto;

import lombok.Data;

import java.util.List;

/**
 * Created on 2025-02-18 by 한상인
 */
@Data
public class IntroduceBlockDto {
    private int blockId;
    private int classId;
    private String blockType;
    //single, double .. + triple이 생길 수도 있을까봐 배열로 만들겠음

    private List<BlockElementDto> elements;
}
