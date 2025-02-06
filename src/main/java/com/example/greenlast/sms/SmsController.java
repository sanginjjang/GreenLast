package com.example.greenlast.sms;

import com.example.greenlast.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    // ✅ 인증번호 전송
    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSms(@RequestBody Map<String, String> request, HttpSession session) {
        String name = request.get("name");
        String phone = request.get("phone");

        if (!smsService.isUserExists(name, phone)) {
            return ResponseEntity.badRequest().body("사용자 정보가 일치하지 않습니다.");
        }

        String code = smsService.sendSmsCode(phone, session);
        return ResponseEntity.ok("인증번호가 전송되었습니다! (테스트용: " + code + ")");
    }

    // ✅ 인증번호 확인
    @PostMapping("/verify-sms")
    public ResponseEntity<?> verifySms(@RequestBody Map<String, String> request, HttpSession session) {
        String name = request.get("name");
        String phone = request.get("phone");
        String authCode = request.get("authCode");

        if (smsService.verifySmsCode(phone, authCode, session)) {
            session.removeAttribute("authCode_" + phone); // ✅ 인증 성공 시 세션 삭제
            UserDTO user = smsService.getUserByNameAndPhone(name, phone);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않습니다.");
        }
    }
}
