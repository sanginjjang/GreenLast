package com.example.greenlast.controllers.views.sangin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2025-01-22 by 한상인
 */
@Controller
@RequestMapping("/view")
public class RegistUserViewController_Sangin {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 생성자 주입으로 BCryptPasswordEncoder 주입
    public RegistUserViewController_Sangin(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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
    
}
