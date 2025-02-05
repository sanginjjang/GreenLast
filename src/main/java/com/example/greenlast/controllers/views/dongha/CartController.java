package com.example.greenlast.controllers.views.dongha;

import com.example.greenlast.dto.CartDTO;
import com.example.greenlast.security.CustomUserDetails;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.dongha.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * packageName    : com.example.greenlast.controllers.views.dongha
 * fileName       : CartController
 * author         : 이동하
 * date           : 25. 2. 2.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 2.        이동하       최초 생성
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String showCart(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";  // ✅ 로그인하지 않은 경우 리디렉트
        }

        String userId = userDetails.getUserId();  // ✅ userId 가져오기
        List<CartDTO> cart = cartService.getCartListByUserId(userId);  // ✅ userId로 장바구니 조회

        System.out.println("📌 [CartController] 사용자 ID: " + userId);
        System.out.println("📌 [CartController] 장바구니 개수: " + cart.size());

        int totalPrice = cart.stream().mapToInt(item -> item.getCartPrice() * item.getQuantity()).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "dongha/cart";
    }

}