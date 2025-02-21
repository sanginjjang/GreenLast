//package com.example.greenlast.controllers.views.dongha;
//
//
//import com.example.greenlast.dao.joontaek.MakeClassDao;
//import com.example.greenlast.dto.*;
//import com.example.greenlast.file.FileEntity;
//import com.example.greenlast.file.FileService;
//import com.example.greenlast.service.dongha.PermitClassService;
//import com.example.greenlast.service.joontaek.MakeClassService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.web.webauthn.api.PublicKeyCredential;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/view/permitClass")
//public class PermitClassController {
//    @Autowired
//    private PermitClassService permitClassService;
//
//    @RequestMapping("/detail")
//    public String viewClassDetail(@RequestParam("classId") int classId, Model model) {
//        ClassDTO classInfo = permitClassService.getClassDetail(classId);
//        model.addAttribute("classInfo", classInfo);
//        return "/dongha/permitClassFirst";
//    }
//
//    @GetMapping("/secondPermit")
//    public String secondPermit(@RequestParam("classId") int classId, Model model) {
//        List<ClassSectionDTO> curriculum = permitClassService.getClassCurriculum(classId);
//        model.addAttribute("curriculum", curriculum);
//        return "/dongha/permitClassSecond";
//    }
//
//    @GetMapping("/thirdPermit")
//    public String lastPermit(@RequestParam("classId") int classId, Model model) {
//        List<IntroduceBlockDto> blocks = permitClassService.getBlocksByClassId(classId);
//
//        for (IntroduceBlockDto block : blocks) {
//            List<BlockElementDto> elements = permitClassService.getElementsByBlockId(block.getBlockId());
//            block.setElements(elements);
//        }
//
//        model.addAttribute("blocks", blocks);
//        model.addAttribute("classId", classId);
//        System.out.println(blocks);
//        return "/dongha/permitClassThird";
//    }
//
//
//}