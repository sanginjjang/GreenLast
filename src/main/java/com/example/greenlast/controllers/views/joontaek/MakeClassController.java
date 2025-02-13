package com.example.greenlast.controllers.views.joontaek;


import com.example.greenlast.dao.joontaek.MakeClassDao;
import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.ContentRequestDTO;
import com.example.greenlast.dto.SectionDTO;
import com.example.greenlast.file.FileService;
import com.example.greenlast.service.joontaek.MakeClassService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/view/makeClass")
@CrossOrigin(origins = "*")
public class MakeClassController {

    @Autowired
    FileService fileService;
    @Autowired
    MakeClassDao makeClassDao;
    @Autowired
    MakeClassService makeClassService;



    @RequestMapping("/first")
    public String first() {

        return "/joontaek/class/makeClassFirst";
    }

    @RequestMapping("/second")
    public String second(@ModelAttribute ClassDTO classInfo, HttpServletRequest request) {

        HttpSession session = request.getSession();

        int classId = makeClassDao.getMaxClassId() + 1;
        classInfo.setClassId(classId);
        session.setAttribute("classInfo", classInfo);
        System.out.println("강의 기본 정보 "+classInfo);
        return "/joontaek/class/makeClassSecond";
    }


    @ResponseBody
    @PostMapping(value = "/third", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> saveCurriculum(
            HttpServletRequest request,
            @RequestPart("curriculumData") String curriculumDataJson,
            @RequestParam(value = "videos[]", required = false) List<MultipartFile> videos
    ) {

        HttpSession session = request.getSession();

        // videos[] 리스트로 받기

        Map<String, Object> response = new HashMap<>();
        try {
            // curriculumData JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> curriculumData = mapper.readValue(curriculumDataJson, List.class);

            // videos 배열 확인
            if (videos != null && !videos.isEmpty()) {
                System.out.println("Received videos:");
                for (int i = 0; i < videos.size(); i++) {
                    System.out.println(String.format("Video[%d]: %s (Size: %d bytes)",
                            i, videos.get(i).getOriginalFilename(), videos.get(i).getSize()));
                }
            } else {
                System.out.println("No videos received");
            }


            response.put("success", true);
            response.put("curriculumId", 1L);

            int classId = makeClassDao.getMaxClassId() + 1;

            session.setAttribute("sectionInfo", curriculumData);
            session.setAttribute("videos", videos);
            for (int a = 0; a < curriculumData.size(); a++) {
                Map<String, Object> section = curriculumData.get(a);

                System.out.println("Curriculum Data: " + section);

                String sectionTitle = section.get("title").toString();
                //여기서 섹션 저장


                List<Map<String, Object>> lessons = (List<Map<String, Object>>) section.get("lessons");

                for (Map<String, Object> lesson : lessons) {
                    String lessonTitle = lesson.get("title").toString(); // 수업이름
                }

                for (int video = 0; video < videos.size(); video++) {

                }

            }


            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        //-------------------저장로직-------------------


    }


    @RequestMapping("/thirdd")
    public String thirdd(HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<Map<String, Object>> sectionInfo = (List<Map<String, Object>>) session.getAttribute("sectionInfo");
        ClassDTO classInfo = (ClassDTO) session.getAttribute("classInfo");
        List<MultipartFile> videos = (List<MultipartFile>) session.getAttribute("videos");

        System.out.println("강의 정보");
        System.out.println(classInfo);
        System.out.println("섹션정보");
        System.out.println(sectionInfo);
        System.out.println("비디오 정보");
        System.out.println(videos);


        return "/joontaek/class/makeClassThird";
    }

    @ResponseBody
    @PostMapping("/last")
    public ResponseEntity<?> saveContent(@RequestBody ContentRequestDTO request) {
        try {
            int classId = makeClassDao.getMaxClassId() + 1;

            for (ContentRequestDTO.BlockData block : request.getContent()) {
                int result = makeClassService.saveBlock(classId, block.getType());
                int blockId = makeClassService.getBlockNum();

                for (ContentRequestDTO.ElementData element : block.getElements()) {

                    if ("image".equals(element.getType())) {
                        // Base64 데이터에서 헤더 부분 제거
                        String base64Image = element.getContent().split(",")[1];



                        // 파일 이름 생성
                        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
                        String filePath = "C:/classInfoImg/" + fileName;

                        // Base64를 파일로 저장
                        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                        Files.write(Paths.get(filePath), imageBytes);

                        // content를 파일 경로로 변경
                        element.setContent(filePath);

                        System.out.println("파일 경로 : " + filePath);
                        System.out.println("파일 이름 : " + fileName);
                    }

                    System.out.println("--- 요소 정보 ---");
                    System.out.println("타입: " + element.getType());
                    System.out.println("내용: " + element.getContent());

                    makeClassService.saveElement(blockId,element.getType(),element.getContent());

                }
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "강의 소개글이 성공적으로 저장되었습니다."
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "저장 중 오류가 발생했습니다.",
                            "error", e.getMessage()
                    ));
        }
    }
//    @ResponseBody
//    @PostMapping("/last")
//    public ResponseEntity<?> saveContent(@RequestBody ContentRequestDTO request) {
//        try {
//
//            //class_id를 최대치+1 시켜서 저장시킴
//            int classId = makeClassDao.getMaxClassId() + 1;
//
//
//            System.out.println("=== 요청 데이터 시작 ===");
//            System.out.println("요청 객체: " + request);
//
//            // content 리스트 확인
//            List<ContentRequestDTO.BlockData> blocks = request.getContent();
//            System.out.println("블록 개수: " + blocks.size());
//
//            // 각 블록의 상세 정보 출력
//            for (int i = 0; i < blocks.size(); i++) {
//                ContentRequestDTO.BlockData block = blocks.get(i);
//                System.out.println("\n=== 블록 " + (i + 1) + " ===");
//                System.out.println("타입: " + block.getType());
//
//                int result = makeClassService.saveBlock(classId, block.getType());
//
//                System.out.println("@@@@@@@@@@@@@@@@@@@@");
//                System.out.println("등록 결과.. @@@@@@@ : :"+result);
//                System.out.println("@@@@@@@@@@@@@@@@@@@@");
//
//                List<ContentRequestDTO.ElementData> elements = block.getElements();
//                System.out.println("요소 개수: " + elements.size());
//
//                for (int j = 0; j < elements.size(); j++) {
//                    ContentRequestDTO.ElementData element = elements.get(j);
//                    System.out.println("--- 요소 " + (j + 1) + " ---");
//                    System.out.println("타입: " + element.getType());
//                    String content = element.getContent();
//
////                    System.out.println("내용: " + (element.getElementType().equals("image")
////                            ? content.substring(0, Math.min(100, content.length())) + "..."
////                            : content));
//
//                    System.out.println("내용: " + content);
//                }
//            }
//            System.out.println("\n=== 요청 데이터 끝 ===");
//
//
//            return ResponseEntity.ok(Map.of(
//                    "success", true,
//                    "message", "강의 소개글이 성공적으로 저장되었습니다."
//            ));
//
//        } catch (Exception e) {
//            e.printStackTrace(); // 에러 스택트레이스 출력
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "success", false,
//                            "message", "저장 중 오류가 발생했습니다.",
//                            "error", e.getMessage()
//                    ));
//        }
//
//    }
}

