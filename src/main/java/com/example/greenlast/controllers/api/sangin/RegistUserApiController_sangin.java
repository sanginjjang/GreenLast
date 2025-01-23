package com.example.greenlast.controllers.api.sangin;

import com.example.greenlast.service.sangin.RegistUserService_sangin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2025-01-23 by 한상인
 */
@RestController
@RequestMapping("/api/")
public class RegistUserApiController_sangin {

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



}
