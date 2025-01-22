package com.example.greenlast.dto;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;

@Data
public class UserPaymentHistoryDTO {
    private int paymentId;
    private int userId;
    private int classId;
    private String paymentDate;
    private double amount;
    private String refundStatus;
}
