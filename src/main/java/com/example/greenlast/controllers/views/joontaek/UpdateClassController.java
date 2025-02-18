package com.example.greenlast.controllers.views.joontaek;


import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.ClassSectionDTO;
import com.example.greenlast.dto.FileDTO;
import com.example.greenlast.file.FileEntity;
import com.example.greenlast.file.FileService;
import com.example.greenlast.service.joontaek.UpdateClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/view/classUpdate")
public class UpdateClassController {

    @Autowired
    UpdateClassService updateClassService;
    @Autowired
    FileService fileService;


    @RequestMapping("/first")
    public String viewClassDetail(@RequestParam("classId") int classId, Model model) {

        System.out.println(classId);
        ClassDTO classInfo = updateClassService.getClassInfo(classId);

        FileDTO thumbnailImg = updateClassService.getThumbnailImg(classInfo.getFileNo());


//        FileEntity file = fileService.getFileById(classInfo.getClassId());
//        String thumbnailUrl = (file != null && file.getFileUrl() != null) ? file.getFileUrl() : "/images/default-thumbnail.jpg";
//
//        System.out.println("@@@@@@@@파일확인@@@@@@@@@@@@@");
//        System.out.println(file != null ? file.getFileNo() : "파일 없음");
//        System.out.println(thumbnailUrl);
//        System.out.println("@@@@@@@@파일확인@@@@@@@@@@@@@");
//
        model.addAttribute("classInfo", classInfo);
        model.addAttribute("thumbnail", thumbnailImg);



        return "/joontaek/class/ClassUpdateFirst";
    }

    @PostMapping("/updateThumbnail")
    public String updateThumbnail(@ModelAttribute ClassDTO classInfo,
                                  @RequestParam("thumbnailFile") MultipartFile thumbnailFile) throws IOException {


        System.out.println("@@@@@@@@@@@@@@@@@@@@");
        System.out.println(classInfo);
        System.out.println("@@@@@@@@@@@@@@@@@@@@");


        int classId = classInfo.getClassId();

        int result = updateClassService.updateClassInfo(classInfo);
        if (!thumbnailFile.isEmpty()) {


            FileEntity file = fileService.saveFile(thumbnailFile,"thumbnail",classId);
        }

        return "redirect:/view/classUpdate/first?classId=" + classId;
    }

//    @GetMapping("/secondPermit")
//    public String secondPermit(@RequestParam("classId") int classId, Model model) {
//        List<ClassSectionDTO> curriculum = permitClassService.getClassCurriculum(classId);
//        model.addAttribute("curriculum", curriculum);
//        return "/dongha/permitClassSecond";
//    }

    @GetMapping("/thirdPermit")
    public String thirdPermit(@RequestParam("classId") int classId, Model model) {
        return "/dongha/permitClassThird";
    }
}
