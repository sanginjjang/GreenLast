package com.example.greenlast.controllers.api.joontaek;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String index() {
        return "index"; // 준택 수정사항
    }

}
