package com.example.greenlast.controllers.api.dongha;

import com.example.greenlast.dto.CartDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.dongha.CartService;
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
 * fileName       : CartControllerBack
 * author         : 이동하
 * date           : 25. 2. 5.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 5.        이동하       최초 생성
 */
@RestController
@RequestMapping("/cart-api")
@RequiredArgsConstructor
public class CartControllerBack {
    private final CartService cartService;
    private final PaymentService paymentService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Map<String, String> requestData) {
        String userId = SecurityUtil.getCurrentUserId();
        String classIdStr = requestData.get("classId");

        if (classIdStr == null || classIdStr.isEmpty()) {
            return ResponseEntity.ok(Map.of("success", false, "message", "잘못된 요청입니다. classId가 없습니다."));
        }

        int classId;
        try {
            classId = Integer.parseInt(classIdStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(Map.of("success", false, "message", "잘못된 요청입니다. classId가 숫자가 아닙니다."));
        }

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.ok(Map.of("success", false, "message", "로그인이 필요합니다."));
        }

        if (cartService.isItemInCart(userId, classId)) {
            return ResponseEntity.ok(Map.of("success", false, "message", "이미 장바구니에 있습니다."));
        }

        cartService.addCart(userId, classId);
        return ResponseEntity.ok(Map.of("success", true, "message", "장바구니에 추가되었습니다."));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CartDTO>> getCart(@RequestParam String userId) {
        List<CartDTO> cartList = cartService.getCartListByUserId(userId);
        return ResponseEntity.ok(cartList);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, Object>> removeFromCart(@RequestParam("classId") int classId) {
        String userId = SecurityUtil.getCurrentUserId();
        cartService.removeCart(userId, classId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "삭제 완료!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> requestData) {
        String userId = SecurityUtil.getCurrentUserId();
        Object purchasedItemsObj = requestData.get("purchasedItems");
        if (!(purchasedItemsObj instanceof List<?>)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "잘못된 데이터 형식"));
        }

        List<Map<String, Object>> purchasedItems;
        try {
            purchasedItems = (List<Map<String, Object>>) purchasedItemsObj;
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "잘못된 데이터 형식"));
        }

        if (purchasedItems.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "결제된 강의 목록이 없습니다."));
        }

        for (Map<String, Object> item : purchasedItems) {
            int classId = Integer.parseInt(item.get("classId").toString());
            int price = Integer.parseInt(item.get("price").toString());
            String receiptId = item.get("receiptId").toString();

            paymentService.savePayment(userId, classId, price, receiptId);
        }

        return ResponseEntity.ok(Map.of("success", true, "message", "결제 내역 저장 완료"));
    }
}