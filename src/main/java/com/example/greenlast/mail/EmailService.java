package com.example.greenlast.mail;

import com.example.greenlast.dao.sangin.UserDao_sangin;
import com.example.greenlast.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserDao_sangin userDao;

    // ✅ 인증번호 전송
    public String sendVerificationCode(String toEmail) {
        String verificationCode = generateCode(); // 6자리 인증번호 생성

        // ✉️ 이메일 작성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("✅ GreenLast 인증번호");
        message.setText("인증번호는: " + verificationCode + " 입니다.");

        mailSender.send(message); // 🚀 메일 전송

        return verificationCode;
    }

    // ✅ 6자리 랜덤 인증번호 생성
    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 100000 ~ 999999
        return String.valueOf(code);
    }

    // ✅ 아이디 찾기: 이름과 이메일로 사용자 존재 여부 확인
    public boolean isUserExists(String name, String email) {
        return userDao.existsByNameAndEmail(name, email);
    }

    // ✅ 비밀번호 찾기: 아이디, 이름, 이메일로 사용자 존재 여부 확인
    public boolean isUserExistsById(String userId, String name, String email) {
        return userDao.existsByIdAndEmail(userId, name, email);
    }

    // ✅ 아이디 찾기: 이름과 이메일로 사용자 정보 조회
    public UserDTO getUserByNameAndEmail(String name, String email) {
        return userDao.getUserByNameAndEmail(name, email);
    }

    // ✅ 비밀번호 찾기: 아이디, 이름, 이메일로 사용자 정보 조회
    public UserDTO getUserByIdAndEmail(String userId, String name, String email) {
        return userDao.getUserByIdAndEmail(userId, name, email);
    }
}
