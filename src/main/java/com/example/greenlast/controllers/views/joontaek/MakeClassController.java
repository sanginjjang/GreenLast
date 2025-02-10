package com.example.greenlast.controllers.views.joontaek;


import com.example.greenlast.dto.ClassDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/view/makeClass")
public class MakeClassController {

    @RequestMapping("/first")
    public String first() {

        System.out.println("들어옴?");
        return "/joontaek/class/makeClassFirst";
    }

    @RequestMapping("/second")
    public String second(@ModelAttribute ClassDTO classInfo) {


        System.out.println("등록정보 : "+classInfo);

        return "/joontaek/class/makeClassSecond";
    }
}
