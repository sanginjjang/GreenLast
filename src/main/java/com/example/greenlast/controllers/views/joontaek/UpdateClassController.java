package com.example.greenlast.controllers.views.joontaek;


import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.ClassSectionDTO;
import com.example.greenlast.dto.FileDTO;
import com.example.greenlast.dto.SectionDTO;
import com.example.greenlast.file.FileEntity;
import com.example.greenlast.file.FileService;
import com.example.greenlast.service.joontaek.UpdateClassService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/view/classUpdate")
public class UpdateClassController {

    @Autowired
    UpdateClassService updateClassService;
    @Autowired
    FileService fileService;


    @RequestMapping("/first")
    public String first(@RequestParam("classId") int classId, Model model,HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.setAttribute("classId", classId);
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


            FileEntity file = fileService.saveFile(thumbnailFile, "thumbnail", classId);
        }

        return "redirect:/view/classUpdate/first?classId=" + classId;
    }

    @PostMapping("/second")
    public String second(@RequestParam("classId") int classId, Model model) {
//        List<ClassSectionDTO> curriculum = updateClassService.getClassCurriculum(classId);

        List<SectionDTO> sections = updateClassService.getSections(classId);

        for (int a = 0; a < sections.size(); a++) {
            System.out.println("각 섹션 : " + sections.get(a).toString());
        }

        model.addAttribute("sections", sections);
        return "/joontaek/class/ClassUpdateSecond";
    }

    @ResponseBody
    @PostMapping("/third")
    public ResponseEntity<?> updateCurriculum(
            HttpServletRequest request,
            @RequestPart("curriculumData") String curriculumDataJson, // JSON 데이터
            @RequestPart(value = "videos", required = false) List<MultipartFile> videos) { // 동영상 파일들

        HttpSession session = request.getSession();
        int classId = (Integer) session.getAttribute("classId");
        List<Long> sectionIdList = updateClassService.getOriSectionId(); // 기존 섹션 ID 리스트
        List<Long> lessonIdList = updateClassService.getOriLessonId();   // 기존 레슨 ID 리스트

        Set<Long> existingSectionIds = new HashSet<>(sectionIdList); // 기존 섹션 ID를 Set으로 변환
        Set<Long> existingLessonIds = new HashSet<>(lessonIdList);   // 기존 레슨 ID를 Set으로 변환

        System.out.println("📌 강의 커리큘럼 업데이트 요청 들어옴!");
        System.out.println("✅ JSON 데이터: " + curriculumDataJson);
        System.out.println("✅ 동영상 데이터: " + videos);

        try {
            // 📌 JSON 데이터를 DTO로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<SectionDTO> sections = objectMapper.readValue(curriculumDataJson, new TypeReference<List<SectionDTO>>() {});

            // ✅ 새로 넘어온 ID를 담을 Set
            Set<Long> receivedSectionIds = new HashSet<>();
            Set<Long> receivedLessonIds = new HashSet<>();

            for (SectionDTO section : sections) {
                System.out.println("✅ 섹션 ID: " + section.getSectionId());
                System.out.println("✅ 섹션 제목: " + section.getTitle());

                receivedSectionIds.add(section.getSectionId()); // 프론트에서 넘어온 섹션 ID 저장

                boolean isNewSection = true; // 새로운 섹션 여부 플래그

                for (Long existingSectionId : sectionIdList) {
                    if (existingSectionId.equals(section.getSectionId())) {
                        // ✅ 기존 섹션이면 업데이트 수행
                        updateClassService.updateSection(section.getTitle(), section.getSectionId());
                        isNewSection = false;
                        break;
                    }
                }

                // ✅ 기존 ID가 없으면 새로운 섹션 추가
                if (isNewSection) {
                    updateClassService.updateNewSection(classId, section.getTitle());
                }

                for (SectionDTO.Lesson lesson : section.getLessons()) {
                    System.out.println("   📚 레슨 ID: " + lesson.getLessonId());
                    System.out.println("   📚 레슨 제목: " + lesson.getTitle());

                    receivedLessonIds.add(lesson.getLessonId()); // 프론트에서 넘어온 레슨 ID 저장

                    boolean isNewLesson = true;

                    for (Long existingLessonId : lessonIdList) {
                        if (existingLessonId.equals(lesson.getLessonId())) {
                            // ✅ 기존 레슨이면 업데이트 수행
                            updateClassService.updateLesson(lesson.getTitle(), lesson.getLessonId());
                            isNewLesson = false;
                            break;
                        }
                    }

                    // ✅ 기존 ID가 없으면 새로운 레슨 추가
                    if (isNewLesson) {
                        updateClassService.updateNewLesson(section.getSectionId(), lesson.getTitle());
                    }

                    // ✅ 비디오 정보 출력
                    if (lesson.getVideo() != null) {
                        System.out.println("   🎥 비디오 파일명: " + lesson.getVideo().getFileName());
                        System.out.println("   🎥 비디오 파일 크기: " + lesson.getVideo().getFileSize());
                    } else {
                        System.out.println("   🚫 영상 없음");
                    }
                }
            }

            System.out.println("------------------------------------");

            // ✅ **삭제할 섹션과 레슨을 식별**
            existingSectionIds.removeAll(receivedSectionIds); // 기존 섹션 중에서 프론트에서 넘어오지 않은 것만 남김
            existingLessonIds.removeAll(receivedLessonIds);   // 기존 레슨 중에서 프론트에서 넘어오지 않은 것만 남김

            // ✅ **실제로 삭제 실행**
            for (Long sectionId : existingSectionIds) {
//                updateClassService.deleteSection(sectionId);
                System.out.println("🗑️ 삭제된 섹션 ID: " + sectionId);
            }
            for (Long lessonId : existingLessonIds) {
//                updateClassService.deleteLesson(lessonId);
                System.out.println("🗑️ 삭제된 레슨 ID: " + lessonId);
            }

            System.out.println("✅ 삭제 완료!");

            return ResponseEntity.ok("강의 커리큘럼이 성공적으로 업데이트되었습니다!");

        } catch (Exception e) {
            System.out.println("❌ 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류 발생 ❌");
        }
    }


}
