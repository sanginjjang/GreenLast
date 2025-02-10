package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.IClassMainDao;
import com.example.greenlast.dao.dongha.IPaymentDao;
import com.example.greenlast.dto.UserPaymentHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public void savePayment(String userId, int classId, int price, String receiptId) {
        int existingPurchase = paymentDao.checkIfAlreadyPurchased(userId, classId);

        if (existingPurchase > 0) {
            throw new RuntimeException("이미 구매한 강의입니다. 환불 후 다시 구매할 수 있습니다.");
        }

        String classTitle = classMainDao.getClassTitleById(classId);

        UserPaymentHistoryDTO payment = new UserPaymentHistoryDTO();
        payment.setUserId(userId);
        payment.setClassId(classId);
        payment.setPrice(price);
        payment.setClassTitle(classTitle);
        payment.setRefundStatus("결제완료");
        payment.setReceiptId(receiptId);

        paymentDao.insertPayment(payment);
    }

    public int checkIfAlreadyPurchased(String userId, int classId) {
        return paymentDao.checkIfAlreadyPurchased(userId, classId);
    }

    public List<UserPaymentHistoryDTO> getPaymentHistory(String userId) {
        List<UserPaymentHistoryDTO> history = paymentDao.getUserPaymentHistory(userId);
        System.out.println("history.size() : " + history.size());
        return history;
    }

    public void processRefund(int paymentId) {
        System.out.println("환불 처리 시작! paymentId: " + paymentId);

        UserPaymentHistoryDTO payment = paymentDao.getPaymentById(paymentId);

        if (payment == null) {
            throw new RuntimeException("결제 정보를 찾을 수 없습니다. paymentId: " + paymentId);
        }

        System.out.println("환불 처리 중 결제 정보: " + payment);

        if (payment.getReceiptId() == null) {
            throw new RuntimeException("영수증 ID가 없다");
        } else {
            System.out.println("영수증 ID 확인 함: " + payment.getReceiptId());
        }

        if (payment.getPaymentDate() == null) {
            throw new RuntimeException("결제 일자 정보가 없습니다.");
        }

        LocalDateTime paymentDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            paymentDate = LocalDateTime.parse(payment.getPaymentDate(), formatter);  // 🔥 파싱한 값 저장
            System.out.println("🔥 결제 일자 확인 완료: " + paymentDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("날짜 포맷이 잘못되었습니다: " + payment.getPaymentDate());
        }

        LocalDateTime now = LocalDateTime.now();

        if (Duration.between(paymentDate, now).toHours() < 3) {
            boolean refundSuccess = callBootpayRefund(payment.getReceiptId(), payment.getPrice());

            if (refundSuccess) {
                paymentDao.updateRefundStatus(paymentId, "환불완료");
            } else {
                throw new RuntimeException("환불 처리 중 오류가 발생했습니다.");
            }
        } else {
            throw new RuntimeException("구매 후 3시간이 지나 자동 환불이 불가능합니다. 관리자에게 문의하십시오.");
        }
    }

    private boolean callBootpayRefund(String receiptId, int price) {
        return true; // 환불 성공 시 true 반환
    }
}
