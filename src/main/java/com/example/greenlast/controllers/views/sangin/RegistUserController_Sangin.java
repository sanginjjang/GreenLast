package com.example.greenlast.controllers.views.sangin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2025-01-22 by 한상인
 */
@Controller
@RequestMapping("/view")
public class RegistUserController_Sangin {
    @GetMapping("/registUserForm")
    public String registUserForm() {
        System.out.println("view/registUserForm...");
        return "/sangin/registUserForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        System.out.println("view/loginForm...");
        return "/sangin/loginForm";
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 생성자 주입으로 BCryptPasswordEncoder 주입
    public RegistUserController_Sangin(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @ResponseBody
    @GetMapping("/sangin/test")
    public String test() {
        System.out.println("sangin/test...");
        String pw = bCryptPasswordEncoder.encode("123");
        return pw;
    }
}
