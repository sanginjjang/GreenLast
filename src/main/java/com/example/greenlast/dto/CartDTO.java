package com.example.greenlast.dto;

import lombok.Data;

/**
 * packageName    : com.example.greenlast.dto
 * fileName       : CartDTO
 * author         : 이동하
 * date           : 25. 2. 2.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 2.        이동하       최초 생성
 */
@Data
public class CartDTO {
    private int cartId;
    private int userId;
    private int classId;
    private String classTitle;
    private int price;
    private int quantity;
}
