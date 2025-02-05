package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.ICartDao;
import com.example.greenlast.dto.CartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.greenlast.service.dongha
 * fileName       : CartService
 * author         : 이동하
 * date           : 25. 2. 2.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 2.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CartService {
    private final ICartDao cartDao;

    public void addCart(String userId, int classId) {
        System.out.println("🛒 [CartService] 장바구니 추가 - userId: " + userId + ", classId: " + classId);
        cartDao.addCart(userId, classId);
        System.out.println("🛒 [CartService] 장바구니 추가 완료!");
    }

    public List<CartDTO> getCartListByUserId(String userId) {
        System.out.println("📌 [CartService] DB 조회 - userId: " + userId);

        List<CartDTO> cartList = cartDao.getCartItems(userId);  // ✅ userId로 장바구니 조회

        for (CartDTO item : cartList) {
            System.out.println("📌 [CartService] 가져온 아이템 - classId: " + item.getClassId() +
                    ", title: " + item.getClassTitle() +
                    ", price: " + item.getCartPrice() +
                    ", userName: " + item.getUserName());
        }

        return cartList;
    }

    public void removeCart(String userId, int classId) {
        cartDao.removeFromCart(userId, classId);
    }

    public boolean isItemInCart(String userId, int classId) {
        return cartDao.checkCartItemExists(userId, classId) > 0;
    }
}
