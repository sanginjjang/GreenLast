package com.example.greenlast.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoLoginController {

    private final String clientId = "a3f955836263dbc92dd134f11969510d";
    private final String redirectUri = "http://localhost:8080/oauth/kakao/callback";

    // ✅ 카카오 로그인 URL 생성 (scope 추가)
    private String getKakaoLoginUrl() {
        return "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=account_email,name,profile_nickname,gender,age_range,birthday,birthyear,phone_number" +
                "&prompt=login";  // 로그인 창 항상 표시
    }

    // ✅ 카카오 로그인 버튼 클릭 시 호출되는 API
    @GetMapping("/oauth/kakao/login")
    public String redirectToKakao() {
        return "redirect:" + getKakaoLoginUrl();
    }
}
