package com.example.greenlast.controllers.api.sangin;

import com.example.greenlast.dto.UserDTO;
import com.example.greenlast.service.sangin.RegistUserService_sangin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2025-01-23 by 한상인
 */
@RestController
@RequestMapping("/api/users")
public class RegistUserApiController_sangin {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 생성자 주입으로 BCryptPasswordEncoder 주입
    public RegistUserApiController_sangin(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private RegistUserService_sangin registUserService;

    @GetMapping("/check-duplicate-id")
    public ResponseEntity<?> checkDuplicateId(@RequestParam String userId) {
        boolean isDuplicate = registUserService.checkUserId(userId);
        System.out.println("아이디 중복검사 tf = " + isDuplicate);

        // JSON 형태로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("duplicate", isDuplicate);
        response.put("message", isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-duplicate-email")
    public ResponseEntity<?> checkDuplicateEmail(@RequestParam String email) {
        boolean isDuplicate = registUserService.checkUserEmail(email);
        System.out.println("email 중복검사 tf = " + isDuplicate);

        // JSON 형태로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("duplicate", isDuplicate);
        response.put("message", isDuplicate ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-duplicate-phoneNumber")
    public ResponseEntity<?> checkDuplicatePhoneNumber(@RequestParam String phoneNumber) {
        boolean isDuplicate = registUserService.checkUserPhoneNumber(phoneNumber);
        System.out.println("phoneNumber 중복검사 tf = " + isDuplicate);

        // JSON 형태로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("duplicate", isDuplicate);
        response.put("message", isDuplicate ? "이미 사용 중인 휴대폰 번호입니다." : "사용 가능한 휴대폰 번호입니다.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/regist-user")
    public ResponseEntity<?> registUser(@RequestBody UserDTO userDTO) {
        System.out.print("api/users/regist-user...");
        System.out.println(userDTO);
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        int result = registUserService.registUser(userDTO);
        if (result == 1) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.badRequest().body(userDTO);
        }
    }
    @ResponseBody
    @PostMapping("/login")
    public void login(@RequestBody UserDTO userDTO) {
        System.out.print("api/users/login...");
        System.out.println(userDTO);
    }


}
