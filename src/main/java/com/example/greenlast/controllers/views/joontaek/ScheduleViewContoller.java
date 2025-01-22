package com.example.greenlast.controllers.views.joontaek;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/schedule")
public class ScheduleViewContoller {

    @RequestMapping("/")
    public String index() {
        return "/joontaek/schedule";
    }
}
