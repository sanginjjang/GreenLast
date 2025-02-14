package com.example.greenlast.controllers.api.dongha;

import com.example.greenlast.dto.*;
import com.example.greenlast.service.dongha.AdminService;
import com.example.greenlast.service.joontaek.MakeClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.example.greenlast.controllers.api.dongha
 * fileName       : ViewAdminController
 * author         : 이동하
 * date           : 25. 1. 23.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 23.        이동하       최초 생성
 */
@RestController
@RequestMapping("/admin-api")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final MakeClassService makeClassService;

    @GetMapping("/daily")
    public ResponseEntity<List<DailyUserDTO>> getDailyUsers() {
        List<DailyUserDTO> daily = adminService.getDailyUsers();
        return ResponseEntity.ok(daily);
    }
    @GetMapping("/age-group")
    public ResponseEntity<List<AgeGroupDTO>> getAgeGroups() {
        List<AgeGroupDTO> ageGroups = adminService.getAgeGroups();
        return ResponseEntity.ok(ageGroups);
    }
    @GetMapping("/gender")
    public ResponseEntity<List<GenderDTO>> getGenders() {
        List<GenderDTO> genders = adminService.getGenders();
        return ResponseEntity.ok(genders);
    }
    @GetMapping("/joinUser")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = adminService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/pending-classes")
    public ResponseEntity<List<ClassDTO>> getPendingClasses() {
        List<ClassDTO> pendingClasses = adminService.getPendingClasses();
        return ResponseEntity.ok(pendingClasses);
    }

    // ✅ 2. 강의 승인 처리 (class_permit을 'y'로 변경)
    @PostMapping("/approve-class")
    public ResponseEntity<Map<String, String>> approveClass(@RequestBody Map<String, Integer> request) {
        int classId = request.get("classId");
        adminService.approveClass(classId);
        return ResponseEntity.ok(Map.of("message", "강의가 승인되었습니다."));
    }

    // ✅ 3. 강의 반려 처리 (반려 메시지 저장 가능)
    @PostMapping("/reject-class")
    public ResponseEntity<Map<String, String>> rejectClass(@RequestBody Map<String, String> request) {
        int classId = Integer.parseInt(request.get("classId"));
        String rejectMessage = request.get("rejectMessage");
        adminService.rejectClass(classId, rejectMessage);
        return ResponseEntity.ok(Map.of("message", "강의가 반려되었습니다."));
    }


}
