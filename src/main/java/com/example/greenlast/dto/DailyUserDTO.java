package com.example.greenlast.dto;

import lombok.Data;

/**
 * packageName    : com.example.greenlast.dto
 * fileName       : DailyUserDTO
 * author         : 이동하
 * date           : 25. 1. 23.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 23.        이동하       최초 생성
 */
@Data
public class DailyUserDTO {
    private String date;
    private int count;
}
