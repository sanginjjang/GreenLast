package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.IClassMainDao;
import com.example.greenlast.dao.dongha.IPaymentDao;
import com.example.greenlast.dto.UserPaymentHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.greenlast.service.dongha
 * fileName       : PaymentService
 * author         : 이동하
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final IPaymentDao paymentDao;
    private final IClassMainDao classMainDao;

    public void savePayment(String userId, int classId, int price){
        String classTitle = classMainDao.getClassTitleById(classId);

        UserPaymentHistoryDTO payment = new UserPaymentHistoryDTO();
        payment.setUserId(userId);
        payment.setClassId(classId);
        payment.setPrice(price);
        payment.setClassTitle(classTitle);
        payment.setRefundStatus("결제 완료");

        paymentDao.insertPayment(payment);

    }

    public List<UserPaymentHistoryDTO> getPaymentHistory(String userId){
        List<UserPaymentHistoryDTO> history = paymentDao.getUserPaymentHistory(userId);
        System.out.println("history.size() : " + history.size());
        return history;
    }

}
