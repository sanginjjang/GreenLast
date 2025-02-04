package com.example.greenlast.mail;

import ch.qos.logback.core.model.Model;
import com.example.greenlast.dao.sangin.UserDao_sangin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created on 2025-02-04 by 한상인
 */
@Controller
public class ResetPasswordController {
    private final UserDao_sangin userDao;

    public ResetPasswordController(UserDao_sangin userDao_sangin) {
        this.userDao = userDao_sangin;
    }

    @GetMapping("/view/resetPassword")
    public String resetPassword(Model model) {
        return "sangin/resetPassword";
    }

    @ResponseBody
    @PostMapping("/api/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String newPassword = request.get("newPassword");

        if (userId == null || newPassword == null) {
            return ResponseEntity.badRequest().body("필수 정보가 누락되었습니다.");
        }

        // ✅ 비밀번호 해싱
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(newPassword);

        // ✅ DB 업데이트
        boolean updated = userDao.updatePassword(userId, hashedPassword);

        if (updated) {
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.status(500).body("비밀번호 변경에 실패했습니다.");
        }
    }
}
