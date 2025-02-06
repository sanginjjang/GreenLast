package com.example.greenlast.login;

import com.example.greenlast.dto.UserDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/oauth/kakao")
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private KakaoUserService kakaoUserService; // 이름 변경

    @GetMapping("/callback")
    public String kakaoCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
        // 1. 카카오로부터 Access Token 받아오기
        String accessToken = kakaoService.getAccessToken(code);

        // 2. 카카오로부터 사용자 정보 받아오기
        Map<String, Object> kakaoUserInfo = kakaoService.getUserInfo(accessToken);

        // 3. 사용자 정보를 DB에 저장하거나 조회
        kakaoUserService.handleKakaoLogin(kakaoUserInfo, response);

        // 4. 로그인 성공 후 리다이렉트
        return "redirect:/";
    }
}

