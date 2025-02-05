package com.example.greenlast.controllers.api.dongha;

import com.example.greenlast.dto.CartDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.dongha.CartService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Map<String, String> requestData) {
        String userId = SecurityUtil.getCurrentUserId();
        String classIdStr = requestData.get("classId");

        if (classIdStr == null || classIdStr.isEmpty()) {
            System.out.println("🚨 [에러] classId가 null 또는 빈 값으로 전달됨!");
            return ResponseEntity.ok(Map.of("success", false, "message", "잘못된 요청입니다. classId가 없습니다."));
        }

        int classId;
        try {
            classId = Integer.parseInt(classIdStr);
        } catch (NumberFormatException e) {
            System.out.println("🚨 [에러] classId 변환 실패! 입력값: " + classIdStr);
            return ResponseEntity.ok(Map.of("success", false, "message", "잘못된 요청입니다. classId가 숫자가 아닙니다."));
        }

        System.out.println("📌 [장바구니 추가 요청] userId: " + userId + ", classId: " + classId);

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


}