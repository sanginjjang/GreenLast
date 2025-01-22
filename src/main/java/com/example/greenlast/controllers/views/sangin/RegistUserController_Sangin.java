package com.example.greenlast.controllers.views.sangin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2025-01-22 by 한상인
 */
@Controller
@RequestMapping("/view")
public class RegistUserController_Sangin {
    @GetMapping("/registUserForm")
    public String registUserForm(){
        System.out.println("view/registUserForm...");
        return "/sangin/registUserForm";
    }
}
