package com.example.greenlast.controllers.main;

import com.example.greenlast.security.CustomUserDetails;
import com.example.greenlast.service.dongha.ClassMainService;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
//    @GetMapping("/")
//    public String homepage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
//        // keyword 값이 null일 수 있으므로 필요한 로직 추가 가능
//        if (keyword != null) {
//            model.addAttribute("class", classMainService.getClassMainByKeyword(keyword));
//        } else {
//            model.addAttribute("class", classMainService.getClassMain());
//        }
//        return "/dongha/home";
//    }

    @GetMapping("/")
    public String homepage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        // ✅ 현재 로그인된 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = null;
        String role = null;

        // ✅ 로그인한 경우에만 사용자 정보 가져오기
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            userId = userDetails.getUsername();
            role = userDetails.getRole(); // ROLE_ADMIN or ROLE_USER
        }

        // ✅ 로그인한 사용자가 관리자면 관리자 페이지로 리디렉트
        if ("ROLE_ADMIN".equals(role)) {
            return "/dongha/chartView";
        }

        // ✅ 로그인 여부와 관계없이 홈페이지 접근 가능하도록 설정
        model.addAttribute("userId", userId); // 로그인 안 했으면 null
        model.addAttribute("role", role); // 로그인 안 했으면 null

        if (keyword != null) {
            model.addAttribute("class", classMainService.getClassMainByKeyword(keyword));
        } else {
            model.addAttribute("class", classMainService.getClassMain());
        }

        return "/dongha/home";
    }

}
