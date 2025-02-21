package com.example.greenlast.controllers.views.sangin;

import com.example.greenlast.dao.sangin.ClassDetailDao_sangin;
import com.example.greenlast.dao.sangin.UserDao_sangin;
import com.example.greenlast.dto.UserDTO;
import com.example.greenlast.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2025-01-22 by 한상인
 */
@Controller
@RequestMapping("/view")
public class SanginController {
    @Autowired
    UserDao_sangin userDao;

    @GetMapping("/setting")
    public String setting(Model model) {
        String userId = SecurityUtil.getCurrentUserId();
        UserDTO user = userDao.getUserByUserId(userId);
        model.addAttribute("userName", user.getName());
        return "/sangin/setting";
    }
//    @GetMapping("/0")
//    @ResponseBody
//    public String test() {
//        return "test";
//    }
//
//    @GetMapping("/1")
//    public String test1(Model model) {
//        String id = SecurityUtil.getCurrentUserId();
//        model.addAttribute("id", id);
//        return "/sangin/test1";
//    }
//
//    @GetMapping("/2")
//    public String test2() {
//        return "/sangin/test2";
//    }
}
