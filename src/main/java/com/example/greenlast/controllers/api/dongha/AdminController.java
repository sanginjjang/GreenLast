package com.example.greenlast.controllers.api.dongha;

import com.example.greenlast.dto.*;
import com.example.greenlast.service.dongha.AdminService;
import com.example.greenlast.service.dongha.UserService_dong;
import com.example.greenlast.service.joontaek.MakeClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private final UserService_dong userService;

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

    @PostMapping("/approve-class")
    public ResponseEntity<Map<String, String>> approveClass(@RequestBody Map<String, Integer> request) {
        int classId = request.get("classId");
        adminService.approveClass(classId);
        return ResponseEntity.ok(Map.of("message", "강의가 승인되었습니다."));
    }

    @PostMapping("/reject-class")
    public ResponseEntity<Map<String, String>> rejectClass(@RequestBody Map<String, String> request) {
        int classId = Integer.parseInt(request.get("classId"));
        String rejectMessage = request.get("rejectMessage");
        adminService.rejectClass(classId, rejectMessage);
        return ResponseEntity.ok(Map.of("message", "강의가 반려되었습니다."));
    }

    @PostMapping("/updateUserStatus")
    public ResponseEntity<Map<String, Object>> updateUserStatus(@RequestBody Map<String, String> requestData) {
        String userId = requestData.get("userId");
        String newRole = requestData.get("role");

        boolean isUpdated = userService.updateUserRole(userId, newRole);

        Map<String, Object> response = new HashMap<>();
        response.put("success", isUpdated);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String userId) {
        boolean isDeleted = userService.deleteUser(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", isDeleted);

        return ResponseEntity.ok(response);
    }

}
