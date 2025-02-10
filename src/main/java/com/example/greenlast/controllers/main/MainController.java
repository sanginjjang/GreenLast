package com.example.greenlast.controllers.main;

import com.example.greenlast.service.dongha.ClassMainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2025-01-21 by 한상인
 */
@Controller
public class MainController {
    private final ClassMainService classMainService;

    public MainController(ClassMainService classMainService) {
        this.classMainService = classMainService;
    }

    //    @GetMapping("/")
//    public String index() {
//        return "/dongha/home";
//    }
    @GetMapping("/")
    public String homepage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        // keyword 값이 null일 수 있으므로 필요한 로직 추가 가능
        if (keyword != null) {
            model.addAttribute("class", classMainService.getClassMainByKeyword(keyword));
        } else {
            model.addAttribute("class", classMainService.getClassMain());
        }
        return "/dongha/home";
    }
    
}
