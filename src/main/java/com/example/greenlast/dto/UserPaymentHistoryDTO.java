package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class UserPaymentHistoryDTO {
    private int paymentId;
    private String userId;
    private int classId;
    private String paymentDate;
    private int price;
    private String refundStatus;
    private String classTitle;
}
