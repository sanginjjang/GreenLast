//package com.example.greenlast.controllers.api.dongha;
//
//import com.example.greenlast.service.dongha.PermitClassService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
///**
// * packageName    : com.example.greenlast.controllers.api.dongha
// * fileName       : PermitClassControllerBack
// * author         : 이동하
// * date           : 25. 2. 13.
// * description    :
// * ===========================================================
// * DATE              AUTHOR             NOTE
// * -----------------------------------------------------------
// * 25. 2. 13.        이동하       최초 생성
// */
//@RestController
//@RequestMapping("/back/permitClass")
//@RequiredArgsConstructor
//public class PermitClassControllerBack {
//    private final PermitClassService permitClassService;
//
//    @PostMapping("/approve")
//    public ResponseEntity<?> approveClass(@RequestBody Map<String, Object> requestData) {
//        int classId = (int) requestData.get("classId");
//        permitClassService.approveReason(classId);
//        return ResponseEntity.ok().body("승인 완료");
//    }
//
//    @PostMapping("/reject")
//    public ResponseEntity<?> rejectClass(@RequestBody Map<String, Object> requestData) {
//        int classId = (int) requestData.get("classId");
//        String reason = requestData.get("reason").toString();
//        permitClassService.insertRejectReason(classId, reason);
//        permitClassService.rejectClass(classId, reason);
//        return ResponseEntity.ok().body("반려 처리 완료");
//    }
//
//
//}
