package com.example.greenlast.controllers.api.dongha;

import com.example.greenlast.dto.UserPaymentHistoryDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.dongha.PaymentService;
import lombok.RequiredArgsConstructor;
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
//        System.out.println("\uD83D\uDFE2 userId = " + userId 이상 무);
        List<UserPaymentHistoryDTO> paymentHistory = paymentService.getPaymentHistory(userId);
        System.out.println("\uD83D\uDFE2 paymentHistory = " + paymentHistory);
        return ResponseEntity.ok(paymentHistory);
    }

    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> requestData){
        String userId = SecurityUtil.getCurrentUserId();

        List<Map<String, Object>> purchasedItems = (List<Map<String, Object>>) requestData.get("purchasedItems");
        if (purchasedItems == null || purchasedItems.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "결제 데이터 누락"));
        }

        for (Map<String, Object> item : purchasedItems) {
            int classId = Integer.parseInt(item.get("classId").toString());  // 🔥 형 변환 추가
            int price = Integer.parseInt(item.get("price").toString());
            paymentService.savePayment(userId, classId, price);
        }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("message", "결제 내역 저장 완료");
        return ResponseEntity.ok(responseData);
    }
}
