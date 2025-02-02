package com.example.greenlast.controllers.views.sangin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2025-01-27 by 한상인
 */
@Controller
@RequestMapping("/view")
public class FindUserInfoViewController_Sangin {
    @GetMapping("/findIdByPhoneForm")
    public String findIdByPhone() {
        System.out.println("findIdByPhoneForm...");
        return "/sangin/findIdByPhoneForm";
    }

    @GetMapping("/findIdByEmailForm")
    public String findIdByEmail() {
        System.out.println("findIdByEmailForm...");
        return "/sangin/findIdByEmailForm";
    }

    @GetMapping("/findPwByPhoneForm")
    public String findPwByPhone() {
        System.out.println("findPwByPhoneForm...");
        return "/sangin/findPwByPhoneForm";
    }

    @GetMapping("/findPwByEmailForm")
    public String findPwByEmail() {
        System.out.println("findPwByEmailForm...");
        return "/sangin/findPwByEmailForm";
    }
}
