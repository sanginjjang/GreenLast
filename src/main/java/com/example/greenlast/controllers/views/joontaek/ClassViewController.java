package com.example.greenlast.controllers.views.joontaek;


import com.example.greenlast.dto.ClassManagementDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.joontaek.ClassDashboardService;
import com.example.greenlast.service.joontaek.ClassManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view/class")
public class ClassViewController {

    @Autowired
    private ClassManagementService classManagementService;

    @Autowired
    private ClassDashboardService dashboardService;


    @RequestMapping("/main")
    public String main(Model model) {


        String userId = SecurityUtil.getCurrentUserId();
        int allStudentCnt = dashboardService.allStudentCnt(userId);
        int allClassCnt = dashboardService.allClassCnt(userId);
        String allRevenue = dashboardService.allRevenue(userId);
        int newStudentCnt = dashboardService.newStudentCnt(userId);
        double ratingAvg = dashboardService.ratingAvg(userId);

        model.addAttribute("studentCnt",allStudentCnt);
        model.addAttribute("classCnt",allClassCnt);
        model.addAttribute("allRevenue",allRevenue);
        model.addAttribute("newStudentCnt",newStudentCnt);
        model.addAttribute("ratingAvg",ratingAvg);


        return "/joontaek/class/main";
    }

    @RequestMapping("/classManagement")
    public String classManagement(Model model) {

        //나중에 userId로 바꿀 예정@@
        String userId = SecurityUtil.getCurrentUserId();
        List<ClassManagementDTO> classManagements = classManagementService.getClassManagementInfo(userId);


        System.out.println("@@@@@@@@@@@@@@@@@@");
        for (int a=0; a<classManagements.size(); a++) {
            System.out.println(classManagements.get(a).toString());
        }

        System.out.println("@@@@@@@@@@@@@@@@@@");


        model.addAttribute("cm",classManagements);



        return "/joontaek/class/classManagement";
    }
}
