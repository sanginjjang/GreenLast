package com.example.greenlast.controllers.views.joontaek;


import com.example.greenlast.dto.*;
import com.example.greenlast.file.FileEntity;
import com.example.greenlast.file.FileService;
import com.example.greenlast.service.dongha.PermitClassService;
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
import java.util.*;

@Controller
@RequestMapping("/view/classUpdate")
public class UpdateClassController {

    @Autowired
    UpdateClassService updateClassService;
    @Autowired
    FileService fileService;

    //동하형 로직
    @Autowired
    PermitClassService permitClassService;


    @RequestMapping("/first")
    public String first(@RequestParam("classId") int classId, Model model, HttpServletRequest request) {

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

//    @ResponseBody
//    @PostMapping("/third")
//    public ResponseEntity<?> updateCurriculum(
//            HttpServletRequest request,
//            @RequestPart("curriculumData") String curriculumDataJson, // JSON 데이터
//            @RequestPart(value = "videos", required = false) List<MultipartFile> videos) { // 동영상 파일들
//
//        HttpSession session = request.getSession();
//        int classId = (Integer) session.getAttribute("classId");
//        List<Long> sectionIdList = updateClassService.getOriSectionId(); // 기존 섹션 ID 리스트
//        List<Long> lessonIdList = updateClassService.getOriLessonId();   // 기존 레슨 ID 리스트
//
//        Set<Long> existingSectionIds = new HashSet<>(sectionIdList); // 기존 섹션 ID를 Set으로 변환
//        Set<Long> existingLessonIds = new HashSet<>(lessonIdList);   // 기존 레슨 ID를 Set으로 변환
//
//        System.out.println("📌 강의 커리큘럼 업데이트 요청 들어옴!");
//        System.out.println("✅ JSON 데이터: " + curriculumDataJson);
//        System.out.println("✅ 동영상 데이터: " + videos);
//
//        try {
//            // 📌 JSON 데이터를 DTO로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            List<SectionDTO> sections = objectMapper.readValue(curriculumDataJson, new TypeReference<List<SectionDTO>>() {});
//
//            // ✅ 새로 넘어온 ID를 담을 Set
//            Set<Long> receivedSectionIds = new HashSet<>();
//            Set<Long> receivedLessonIds = new HashSet<>();
//
//            for (SectionDTO section : sections) {
//                System.out.println("✅ 섹션 ID: " + section.getSectionId());
//                System.out.println("✅ 섹션 제목: " + section.getTitle());
//
//                receivedSectionIds.add(section.getSectionId()); // 프론트에서 넘어온 섹션 ID 저장
//
//                boolean isNewSection = true; // 새로운 섹션 여부 플래그
//
//                for (Long existingSectionId : sectionIdList) {
//                    if (existingSectionId.equals(section.getSectionId())) {
//                        // ✅ 기존 섹션이면 업데이트 수행
//                        updateClassService.updateSection(section.getTitle(), section.getSectionId());
//                        isNewSection = false;
//                        break;
//                    }
//                }
//
//                // ✅ 기존 ID가 없으면 새로운 섹션 추가
//                if (isNewSection) {
//                    updateClassService.updateNewSection(classId, section.getTitle());
//                }
//
//                for (SectionDTO.Lesson lesson : section.getLessons()) {
//                    System.out.println("   📚 레슨 ID: " + lesson.getLessonId());
//                    System.out.println("   📚 레슨 제목: " + lesson.getTitle());
//
//                    receivedLessonIds.add(lesson.getLessonId()); // 프론트에서 넘어온 레슨 ID 저장
//
//                    boolean isNewLesson = true;
//
//                    for (Long existingLessonId : lessonIdList) {
//                        if (existingLessonId.equals(lesson.getLessonId())) {
//                            // ✅ 기존 레슨이면 업데이트 수행
//                            updateClassService.updateLesson(lesson.getTitle(), lesson.getLessonId());
//                            isNewLesson = false;
//                            break;
//                        }
//                    }
//
//                    // ✅ 기존 ID가 없으면 새로운 레슨 추가
//                    if (isNewLesson) {
//                        updateClassService.updateNewLesson(section.getSectionId(), lesson.getTitle());
//                    }
//
//                    // ✅ 비디오 정보 출력
//                    if (lesson.getVideo() != null) {
//                        System.out.println("   🎥 비디오 파일명: " + lesson.getVideo().getFileName());
//                        System.out.println("   🎥 비디오 파일 크기: " + lesson.getVideo().getFileSize());
//                    } else {
//                        System.out.println("   🚫 영상 없음");
//                    }
//                }
//            }
//
//            System.out.println("------------------------------------");
//
//            // ✅ **삭제할 섹션과 레슨을 식별**
//            existingSectionIds.removeAll(receivedSectionIds); // 기존 섹션 중에서 프론트에서 넘어오지 않은 것만 남김
//            existingLessonIds.removeAll(receivedLessonIds);   // 기존 레슨 중에서 프론트에서 넘어오지 않은 것만 남김
//
//            // ✅ **실제로 삭제 실행**
//            for (Long sectionId : existingSectionIds) {
////                updateClassService.deleteSection(sectionId);
//                System.out.println("🗑️ 삭제된 섹션 ID: " + sectionId);
//            }
//            for (Long lessonId : existingLessonIds) {

    /// /                updateClassService.deleteLesson(lessonId);
//                System.out.println("🗑️ 삭제된 레슨 ID: " + lessonId);
//            }
//
//            System.out.println("✅ 삭제 완료!");
//
//            return ResponseEntity.ok("강의 커리큘럼이 성공적으로 업데이트되었습니다!");
//
//        } catch (Exception e) {
//            System.out.println("❌ 예외 발생: " + e.getMessage());
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류 발생 ❌");
//        }
//    }
//    @ResponseBody
//    @PostMapping("/third")
//    public ResponseEntity<?> updateCurriculum(
//            HttpServletRequest request,
//            @RequestPart("curriculumData") String curriculumDataJson, // JSON 데이터
//            @RequestPart(value = "videos", required = false) List<MultipartFile> videos,
//            @RequestParam(value = "existingVideoFileNames", required = false) List<String> existingFileNames,
//            @RequestParam(value = "existingVideoFileSizes", required = false) List<String> existingFileSizes) { // 동영상 파일들
//
//        HttpSession session = request.getSession();
//        int classId = (Integer) session.getAttribute("classId");
//        List<Long> sectionIdList = updateClassService.getSectionIdByClassId(classId);
//
//
//        System.out.println("📌 강의 커리큘럼 업데이트 요청 들어옴!");
//        System.out.println("✅ JSON 데이터: " + curriculumDataJson);
//        System.out.println("✅ 동영상 데이터: " + videos);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@");
//        System.out.println("강의 번호 : " + classId);
//        System.out.println("섹션 번호 : " + sectionIdList);
//        int a=0;
//        System.err.println("@@@@@@@@@@@@@@@@@@@@@@");
//        System.err.println("@@@@@@@@@@@@@@@@@@@@@@");
//        System.err.println("@@@@@@@@@@@@@@@@@@@@@@");
//
//
//        try {
//            // 📌 JSON 데이터를 DTO로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            List<SectionDTO> sections = objectMapper.readValue(curriculumDataJson, new TypeReference<List<SectionDTO>>() {
//            });
//
//            // ✅ 1. 기존 데이터 삭제
////            updateClassService.deleteAllSectionsByClassId(classId);
//            for (int cnt = 0; cnt < sectionIdList.size(); cnt++) {
////                updateClassService.deleteAllLessonsBySectionId(sectionIdList.get(cnt));
//            }
//
//            // ✅ 2. 새로운 데이터 저장
//            for (SectionDTO section : sections) {
//                System.out.println("1. 새 섹션 추가: " + section.getTitle());

    /// /                updateClassService.insertSection(classId, section.getTitle());
//                Long newSectionId = updateClassService.getMaxSectionId();
//
//
//                for (SectionDTO.Lesson lesson : section.getLessons()) {
//                    // ✅ 비디오 파일이 있을 경우 저장
//                    if (lesson.getVideo() != null && videos != null) {
//                        for (MultipartFile video : videos) {
//                            int lessonId = Math.toIntExact(lesson.getLessonId());
//
//                            if (video.getOriginalFilename().equals(lesson.getVideo().getFileName())) {
//                                System.out.println("여기는 원래 있던 동영상 수정임");
//
//                                System.out.println("2. 원래 있던 동영상 : " + existingFileNames.get(a));
//                                System.out.println("3. 레슨 새로 추가(원래 있던 동영상이면 FileNo 찾아서 저장) : " + lesson.getTitle());
//                                a++;
//                            } else {
//                                System.out.println("여긴 새로운 동영상 수정임");
//                                System.out.println("2.새로운 동영상 : " + video.getOriginalFilename());
//                                System.out.println("3.새로운 레슨 추가: " + lesson.getTitle());
//                            }
//
//                        }
//                    }
//                }
//            }
//
//            System.out.println("✅ 모든 섹션 및 레슨 저장 완료!");
//            return ResponseEntity.ok("강의 커리큘럼이 성공적으로 업데이트되었습니다!");
//
//        } catch (Exception e) {
//            System.out.println("❌ 예외 발생: " + e.getMessage());
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류 발생 ❌");
//        }
//    }
    @ResponseBody
    @PostMapping("/third")
    public ResponseEntity<?> updateCurriculum(
            HttpServletRequest request,
            @RequestPart("curriculumData") String curriculumDataJson, // JSON 데이터
            @RequestPart(value = "videos", required = false) List<MultipartFile> videos) throws IOException{ // 동영상 파일들

        try {
            // 📌 JSON 데이터를 DTO로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<SectionDTO> sections = objectMapper.readValue(curriculumDataJson, new TypeReference<List<SectionDTO>>() {
            });

            HttpSession session = request.getSession();


            int videoCnt = 0;
            int classId = (Integer)session.getAttribute("classId");
            List<Long> sectionIdList = updateClassService.getSectionIdByClassId(classId);


            List<LessonDTO> lessonInfos = new ArrayList<>();

            for (SectionDTO section : sections) {
                for (int i = 0; i < section.getLessons().size(); i++) {
                    Long lessonId = section.getLessons().get(i).getLessonId();
                    List<LessonDTO> lessonInfoList = updateClassService.getLessonInfoByLessonId(lessonId);
                    // ✅ lessonId로 레슨 정보 리스트 가져오기
                    lessonInfos.addAll(lessonInfoList);

                    // ✅ lessonInfoList가 비어 있는 경우 체크
                    if (lessonInfoList == null || lessonInfoList.isEmpty()) {
                        System.out.println(i + "번째 레슨은 null입니다.");
                        continue;
                    }

                    // ✅ lessonInfoList에 있는 모든 레슨 출력
                    for (LessonDTO lessonInfo : lessonInfoList) {
                        System.out.println(i + "번째 레슨 정보 : " + lessonInfo.toString());
                    }
                }
            }


            //섹션이랑 레슨 삭제다 제발.............,.,.,.,
            updateClassService.deleteAllSectionsByClassId(classId);
            for (int cnt = 0; cnt < sectionIdList.size(); cnt++) {
                updateClassService.deleteAllLessonsBySectionId(sectionIdList.get(cnt));
            }

            for (SectionDTO section : sections) {
                System.out.println("✅ 섹션 ID: " + section.getSectionId() + " | 제목: " + section.getTitle());

                if(section.getSectionId() != null){
                    updateClassService.insertOriSection(section.getSectionId(),classId,section.getTitle());
                    System.out.println("수정 시 기존에 있던 섹션이면 섹션번호 그대로 저장 : " + section.getSectionId());
                    System.out.println("섹션 제목 : " + section.getTitle());


                }else{
                    updateClassService.insertNewSection(classId,section.getTitle());
                    System.out.println("수정 시 새로운 섹션이면 섹션번호 새로 추가 : ");
                    System.out.println("섹션 제목 : " + section.getTitle());
                }

                for (SectionDTO.Lesson lesson : section.getLessons()) {
                    System.out.println("   📚 레슨 ID: " + lesson.getLessonId() + " | 제목: " + lesson.getTitle());

                    if (lesson.getVideo() != null) {
                        SectionDTO.Lesson.Video video = lesson.getVideo();
                        System.out.println("       🎥 영상 정보: FileNo: " + video.getFileNo() + " | 파일명: " + video.getFileName());

                        if(lesson.getLessonId() != null){
                            Long newSectionId = updateClassService.getMaxSectionId();
                            updateClassService.insertLesson(lesson.getLessonId(),newSectionId,lesson.getTitle(),video.getFileNo());
                        }else{
                            fileService.saveFile(videos.get(videoCnt),"video",0);
                            int fileNo = updateClassService.getMaxFileNo();
                            Long newSectionId = updateClassService.getMaxSectionId();
                            updateClassService.insertNewLesson(newSectionId,lesson.getTitle(),fileNo);
                            videoCnt++;
                        }
                    } else {
                        System.out.println("       🚫 영상 없음");
                    }
                }
            }

            return ResponseEntity.ok("JSON 파싱 완료!");

        } catch (Exception e) {
            System.out.println("❌ JSON 파싱 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류 발생 ❌");
        }
    }

    @RequestMapping("/last")
    public String last(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        int classId = (Integer) session.getAttribute("classId");


        List<IntroduceDTO> introduceInfoList = updateClassService.getIntroduceInfo(classId);

//        List<IntroduceBlockDto> blocks = permitClassService.getBlocksByClassId(classId);
//
//        for (IntroduceBlockDto block : blocks) {
//            List<BlockElementDto> elements = permitClassService.getElementsByBlockId(block.getBlockId());
//            block.setElements(elements);
//        }

        model.addAttribute("introduceList", introduceInfoList);
        return "joontaek/class/ClassUpdateLast";
    }







}
