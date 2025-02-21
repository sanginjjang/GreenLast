package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.IClassMainDao;
import com.example.greenlast.dao.dongha.IPaymentDao;
import com.example.greenlast.dto.UserPaymentHistoryDTO;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Cancel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
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
        payment.setRefundStatus("N");
        payment.setReceiptId(receiptId);

        paymentDao.insertPayment(payment);
    }

    public int checkIfAlreadyPurchased(String userId, int classId) {
        return paymentDao.checkIfAlreadyPurchased(userId, classId);
    }

    public List<UserPaymentHistoryDTO> getPaymentHistory(String userId) {
        List<UserPaymentHistoryDTO> history = paymentDao.getUserPaymentHistory(userId);
        return history;
    }

    public void processRefund(int paymentId) {
        UserPaymentHistoryDTO payment = paymentDao.getPaymentById(paymentId);

        if (payment == null) {
            throw new RuntimeException("결제 정보를 찾을 수 없습니다. paymentId: " + paymentId);
        }

        if (payment.getReceiptId() == null) {
            throw new RuntimeException("영수증 ID가 없다");
        }

        if (payment.getPaymentDate() == null) {
            throw new RuntimeException("결제 일자 정보가 없습니다.");
        }

        LocalDateTime paymentDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            paymentDate = LocalDateTime.parse(payment.getPaymentDate(), formatter);  // 🔥 파싱한 값 저장

        } catch (DateTimeParseException e) {
            throw new RuntimeException("날짜 포맷이 잘못되었습니다: " + payment.getPaymentDate());
        }

        LocalDateTime now = LocalDateTime.now();

        if (Duration.between(paymentDate, now).toHours() < 3) {
            boolean refundSuccess = callBootpayRefund(payment.getReceiptId(), payment.getPrice());

            if (refundSuccess) {
                paymentDao.updateRefundStatus(paymentId, "Y");
            } else {
                throw new RuntimeException("환불 처리 중 오류가 발생했습니다.");
            }
        } else {
            throw new RuntimeException("구매 후 3시간이 지나 자동 환불이 불가능합니다. 관리자에게 문의하십시오.");
        }
    }
    private boolean callBootpayRefund(String receiptId, int price) {
        try {
            Bootpay bootpay = new Bootpay("679f11d7cc5274a3ac3fcc2c", "kPNI/PkTT4cJeMp8QCSTaxQ/InukzDgRQpvFpM6y33M=");
            HashMap<String, Object> tokenResponse = bootpay.getAccessToken();

            if (tokenResponse.get("error_code") != null) {
                System.err.println("🔥 토큰 발급 실패: " + tokenResponse);
                return false;
            }

            Cancel cancelRequest = new Cancel();
            cancelRequest.receiptId = receiptId;
            cancelRequest.cancelUsername = "관리자";
            cancelRequest.cancelMessage = "사용자 요청 환불";
            cancelRequest.cancelPrice = (double) price;

            HashMap<String, Object> refundResponse = bootpay.receiptCancel(cancelRequest);

            if (refundResponse.get("error_code") == null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("🔥 환불 요청 중 예외 발생: " + e.getMessage());
            return false;
        }
    }
}
