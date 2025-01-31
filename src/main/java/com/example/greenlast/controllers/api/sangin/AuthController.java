package com.example.greenlast.controllers.api.sangin;

/**
 * Created on 2025-01-23 by 한상인
 */
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // 인증 관련 요청을 처리
public class AuthController {

    // 로그인 요청 처리 (JWT 발급은 필터에서 처리)
    @PostMapping("/login")
    public String login() {
        return "Login successful"; // 로그인 성공 메시지만 반환
    }

    // 로그아웃 요청 처리 (JWT 삭제는 필터에서 처리)
    @PostMapping("/logout")
    public String logout() {
        return "Logout successful"; // 로그아웃 성공 메시지만 반환
    }


}