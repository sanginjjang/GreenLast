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

    public void addCart(int userId, int classId){
        cartDao.addCart(userId, classId);
    }

    public List<CartDTO> getCartList(int userId){
        return cartDao.getCartItems(userId);
    }

    public void removeCart(int userId){
        cartDao.deleteCart(userId);
    }
}
