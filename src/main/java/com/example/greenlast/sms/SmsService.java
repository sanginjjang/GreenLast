package com.example.greenlast.sms;

import com.example.greenlast.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsDao smsDao;

    // ✅ 인증번호 전송
    public String sendSmsCode(String phoneNumber, HttpSession session) {
        String code = generateCode(); // 인증번호 생성
        session.setAttribute("authCode_" + phoneNumber, code); // 세션에 저장
        session.setMaxInactiveInterval(300); // 5분 유효

        System.out.println("인증번호 전송 (테스트용): " + code);
        return code;
    }

    // ✅ 인증번호 검증
    public boolean verifySmsCode(String phone, String authCode, HttpSession session) {
        String sessionCode = (String) session.getAttribute("authCode_" + phone);
        return authCode.equals(sessionCode);
    }

    // ✅ 사용자 존재 여부 확인
    public boolean isUserExists(String name, String phone) {
        return smsDao.existsByNameAndPhone(name, phone);
    }

    // ✅ 사용자 정보 가져오기
    public UserDTO getUserByNameAndPhone(String name, String phone) {
        return smsDao.getUserByNameAndPhone(name, phone);
    }

    // ✅ 6자리 인증번호 생성
    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
