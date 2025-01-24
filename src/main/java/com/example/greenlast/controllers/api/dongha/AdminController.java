package com.example.greenlast.controllers.api.dongha;

import com.example.greenlast.dto.AgeGroupDTO;
import com.example.greenlast.dto.DailyUserDTO;
import com.example.greenlast.dto.GenderDTO;
import com.example.greenlast.dto.UserDTO;
import com.example.greenlast.service.dongha.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//    @GetMapping("/joinUser")
//    public ResponseEntity<List<UserDTO>> getUsers() {
//        List
//    }
}
