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
        cartDao.addCart(userId, classId);
    }

    public List<CartDTO> getCartListByUserId(String userId) {
        List<CartDTO> cartList = cartDao.getCartItems(userId);
        return cartList;
    }

    public void removeCart(String userId, int classId) {
        cartDao.removeFromCart(userId, classId);
    }

    public boolean isItemInCart(String userId, int classId) {
        return cartDao.checkCartItemExists(userId, classId) > 0;
    }

    public void removePurchasedItems(String userId, List<Integer> purchasedItems) {
        for (int classId : purchasedItems) {
            cartDao.removeFromCart(userId, classId);
        }
    }
}
