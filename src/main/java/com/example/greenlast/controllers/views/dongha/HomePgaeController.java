package com.example.greenlast.controllers.views.dongha;

import com.example.greenlast.security.CustomUserDetails;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.dongha.ClassMainService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.bcel.ClassPathManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//    @GetMapping("/homepage")
//    public String homepage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
//        if (keyword != null) {
//            model.addAttribute("classList", classMainService.getClassMainByKeyword(keyword));
//        } else {
//            model.addAttribute("classList", classMainService.getClassMain());
//        }
//        return "/dongha/home";
//    }


    @GetMapping("/detail/{id}")
    public String classDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("classDetail", classMainService.getClassDetail(id));

        if (userDetails != null) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("userId", userDetails.getUsername()); // ✅ userId 추가 전달
        } else {
            model.addAttribute("isAuthenticated", false);
            model.addAttribute("userId", null);
        }
        model.addAttribute("classId", id);

        return "dongha/classDetail";
    }


}
