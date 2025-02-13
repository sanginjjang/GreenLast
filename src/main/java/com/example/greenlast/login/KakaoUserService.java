package com.example.greenlast.login;

import com.example.greenlast.dao.sangin.UserDao_sangin;
import com.example.greenlast.dto.UserDTO;
import com.example.greenlast.security.CookieUtil;
import com.example.greenlast.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class KakaoUserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDao_sangin userDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void handleKakaoLogin(Map<String, Object> kakaoUserInfo, HttpServletResponse response) throws IOException {
        Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoUserInfo.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) kakaoUserInfo.get("properties");

        // ⚠️ Null 체크
        if (kakaoAccount == null || properties == null) {
            response.sendRedirect("/error");
            return;
        }

        // ✅ 사용자 정보 추출
        String email = (String) kakaoAccount.get("email");
        String nickname = (String) properties.get("nickname");
        String name = (String) properties.get("name");
        String gender = (String) kakaoAccount.get("gender");
        gender = "male".equals(gender) ? "M" : "F";

        String ageRange = (String) kakaoAccount.get("age_range");
        String birthday = (String) kakaoAccount.get("birthday");
        String birthyear = (String) kakaoAccount.get("birthyear");
        // ✅ 전화번호 포맷 변경 (+82 10-1234-1234 → 01012341234)
        String phoneNumber = (String) kakaoAccount.get("phone_number");
        if (phoneNumber != null) {
            phoneNumber = phoneNumber.replace("+82 ", "0")  // +82 → 0
                    .replaceAll("-", ""); // 하이픈 제거
        }

        int birth = 0;
        int year = Integer.parseInt(birthyear);
        int dayMonth = Integer.parseInt(birthday);
        birth = (year * 10000) + dayMonth;

        String password = bCryptPasswordEncoder.encode(email);

        // ✅ DB 조회 및 회원가입 로직
        UserDTO user = userDao.findByEmail(email);
        if (user == null) {
            user = new UserDTO();
            user.setEmail(email);
            user.setGender(gender);
            user.setBirth(birth);
            user.setName(nickname);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            userDao.insertUser(user);
        }

        // ✅ JWT 생성 및 쿠키 저장
        UserDTO findUser = userDao.findByEmail(user.getEmail());

        String token = jwtTokenProvider.createToken(findUser.getUserId(), findUser.getRole());
        CookieUtil.addTokenToCookie(response, token);

        response.sendRedirect("/");
    }
}
