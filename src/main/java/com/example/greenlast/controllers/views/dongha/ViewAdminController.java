package com.example.greenlast.controllers.views.dongha;

import com.example.greenlast.service.dongha.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * packageName    : com.example.greenlast.controllers.views.dongha
 * fileName       : ViewAdminController
 * author         : 이동하
 * date           : 25. 1. 23.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 23.        이동하       최초 생성
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ViewAdminController {
    private final AdminService adminService;

    @GetMapping("/chartView")
    public String chart(Model model) {
        model.addAttribute("daily", adminService.getDailyUsers());
        model.addAttribute("ageGroup", adminService.getAgeGroups());
        model.addAttribute("gender", adminService.getGenders());
        return "/dongha/chartView";
    }

    @GetMapping("/classView")
    public String classView(Model model) {
        return null;
    }

    @GetMapping("/userView")
    public String userView(Model model) {
        return "/dongha/userView";
    }

}
