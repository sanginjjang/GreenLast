package com.example.greenlast.mail;

import com.example.greenlast.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // ✅ 인증번호 전송 API (아이디 찾기 & 비밀번호 찾기 공통)
    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestBody UserDTO userDTO, HttpSession session) {
        String name = userDTO.getName();
        String email = userDTO.getEmail();
        String userId = userDTO.getUserId();  // 비밀번호 찾기 시에만 필요

        // ✅ 사용자 확인
        boolean userExists;
        if (userId != null && !userId.isEmpty()) {
            userExists = emailService.isUserExistsById(userId, name, email);  // 비밀번호 찾기
        } else {
            userExists = emailService.isUserExists(name, email);  // 아이디 찾기
        }

        if (!userExists) {
            return ResponseEntity.badRequest().body("해당 정보로 등록된 사용자가 없습니다.");
        }

        // ✅ 인증번호 전송
        String code = emailService.sendVerificationCode(email);
        System.out.println("인증번호: " + code);

        // ✅ 세션에 인증 코드 저장 (10분 유효)
        session.setAttribute("authCode", code);
        session.setMaxInactiveInterval(600);  // 10분

        return ResponseEntity.ok("인증번호가 전송되었습니다!");
    }

    // ✅ 인증번호 검증 API (아이디 찾기)
    @PostMapping("/verify-code/id")
    public ResponseEntity<?> verifyCodeForId(@RequestBody Map<String, String> request, HttpSession session) {
        String name = request.get("name");
        String email = request.get("email");
        String inputCode = request.get("authCode");

        return verifyAuthCode(session, inputCode, () -> {
            UserDTO user = emailService.getUserByNameAndEmail(name, email);
            if (user != null) {
                return ResponseEntity.ok(Map.of("userId", user.getUserId()));
            } else {
                return ResponseEntity.status(404).body("사용자 정보를 찾을 수 없습니다.");
            }
        });
    }

    // ✅ 인증번호 검증 API (비밀번호 찾기)
    @PostMapping("/verify-code/pw")
    public ResponseEntity<?> verifyCodeForPw(@RequestBody Map<String, String> request, HttpSession session) {
        String userId = request.get("userId");
        String name = request.get("name");
        String email = request.get("email");
        String inputCode = request.get("authCode");

        return verifyAuthCode(session, inputCode, () -> {
            UserDTO user = emailService.getUserByIdAndEmail(userId, name, email);
            if (user != null) {
                return ResponseEntity.ok(Map.of("password", user.getPassword()));
            } else {
                return ResponseEntity.status(404).body("사용자 정보를 찾을 수 없습니다.");
            }
        });
    }

    // ✅ 인증번호 검증 로직 공통 처리
    private ResponseEntity<?> verifyAuthCode(HttpSession session, String inputCode, VerificationCallback callback) {
        String sessionCode = (String) session.getAttribute("authCode");

        if (sessionCode == null) {
            return ResponseEntity.status(400).body("인증 시간이 만료되었습니다. 다시 요청하세요.");
        }

        if (sessionCode.equals(inputCode)) {
            session.removeAttribute("authCode");
            return callback.onSuccess();
        } else {
            return ResponseEntity.status(400).body("인증번호가 일치하지 않습니다.");
        }
    }

    // ✅ 콜백 인터페이스 정의
    @FunctionalInterface
    private interface VerificationCallback {
        ResponseEntity<?> onSuccess();
    }
}
