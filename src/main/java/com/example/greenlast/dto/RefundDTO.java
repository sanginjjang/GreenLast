package com.example.greenlast.dto;

import lombok.Data;

/**
 * packageName    : com.example.greenlast.dto
 * fileName       : RefundDTO
 * author         : 이동하
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        이동하       최초 생성
 */
@Data
public class RefundDTO {
    private int refundId;
    private int paymentId;
    private String userId;
    private String refundReason;
    private String refundDate;
    private String refundStatus;
    private String receiptId;
}
