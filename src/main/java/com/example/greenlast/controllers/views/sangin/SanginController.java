package com.example.greenlast.controllers.views.sangin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2025-01-22 by 한상인
 */
@Controller
@RequestMapping("/sangin/test")
public class SanginController {

    @GetMapping("/0")
    @ResponseBody
    public String test() {
        return "test";
    }

    @GetMapping("/1")
    public String test1() {
        System.out.println("view/sangin/test1");
        return "/sangin/test1";
    }

    @GetMapping("/2")
    public String test2() {
        System.out.println("view/sangin/test2");
        return "/sangin/test2";
    }
}
