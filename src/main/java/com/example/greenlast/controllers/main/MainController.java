package com.example.greenlast.controllers.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2025-01-21 by 한상인
 */
@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "/dongha/home";
    }
    
}
