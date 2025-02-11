package com.example.greenlast.controllers.api.dongha;

import com.example.greenlast.dto.UserPaymentHistoryDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.dongha.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.example.greenlast.controllers.api.dongha
 * fileName       : PaymentControllerBack
 * author         : 이동하
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        이동하       최초 생성
 */
@RestController
@RequestMapping("/payment-api")
@RequiredArgsConstructor
public class PaymentControllerBack {
    private final PaymentService paymentService;

    @GetMapping("/history")
    public ResponseEntity<List<UserPaymentHistoryDTO>> getHistory() {
        String userId = SecurityUtil.getCurrentUserId();
        List<UserPaymentHistoryDTO> paymentHistory = paymentService.getPaymentHistory(userId);
        return ResponseEntity.ok(paymentHistory);
    }

    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> requestData) {
        String userId = SecurityUtil.getCurrentUserId();

        List<Map<String, Object>> purchasedItems = (List<Map<String, Object>>) requestData.get("purchasedItems");

        if (purchasedItems == null || purchasedItems.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "결제 데이터가 누락되었습니다."));
        }

        for (Map<String, Object> item : purchasedItems) {
            if (item.get("classId") == null || item.get("price") == null || item.get("receiptId") == null) {
                return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "필수 결제 데이터가 누락되었습니다."));
            }

            int classId = Integer.parseInt(item.get("classId").toString());
            int price = Integer.parseInt(item.get("price").toString());
            String receiptId = item.get("receiptId").toString();

            paymentService.savePayment(userId, classId, price, receiptId);
        }

        return ResponseEntity.ok(Map.of("status", "success", "message", "결제 내역 저장 완료"));
    }

    @PostMapping("/check-duplicate")
    public ResponseEntity<Map<String, Object>> checkDuplicatePurchase(@RequestBody Map<String, Object> requestData) {
        String userId = SecurityUtil.getCurrentUserId();
        List<Map<String, Object>> purchasedItems = (List<Map<String, Object>>) requestData.get("purchasedItems");

        for (Map<String, Object> item : purchasedItems) {
            int classId = Integer.parseInt(item.get("classId").toString());
            int existingPurchase = paymentService.checkIfAlreadyPurchased(userId, classId);

            if (existingPurchase > 0) {
                return ResponseEntity.ok(Map.of("status", "fail", "message", "이미 구매한 강의가 포함되어 있습니다."));
            }
        }

        return ResponseEntity.ok(Map.of("status", "success", "message", "구매 가능한 강의입니다."));
    }

    @PostMapping("/refund")
    public ResponseEntity<Map<String, Object>> requestRefund(@RequestBody Map<String, Object> requestData) {

        if (!requestData.containsKey("paymentId")) {
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "결제 ID가 전달되지 않았습니다."));
        }

        int paymentId = Integer.parseInt(requestData.get("paymentId").toString());

        try {
            paymentService.processRefund(paymentId);
            return ResponseEntity.ok(Map.of("status", "success", "message", "환불이 완료되었습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status", "fail", "message", e.getMessage()));
        }
    }
}
