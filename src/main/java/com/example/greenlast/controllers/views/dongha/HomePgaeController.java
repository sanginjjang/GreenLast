package com.example.greenlast.controllers.views.dongha;

import com.example.greenlast.service.dongha.ClassMainService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.bcel.ClassPathManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.example.greenlast.controllers.views.dongha
 * fileName       : HomePgaeController
 * author         : 이동하
 * date           : 25. 1. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 25.        이동하       최초 생성
 */
@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomePgaeController {
    private final ClassMainService classMainService;

    @GetMapping("/homepage")
    public String homepage(Model model) {
        model.addAttribute("class", classMainService.getClassMain());
        return "/dongha/home";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("classDetail", classMainService.getClassDetail(id));
        return "/dongha/classDetail";
    }
}
