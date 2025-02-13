package com.example.greenlast.controllers.api.sangin;

import com.example.greenlast.dto.ClassIntroduceDTO;
import com.example.greenlast.dto.ClassReviewDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.sangin.ClassDetailService_sangin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2025-02-10 by 한상인
 */
@RestController
@RequestMapping("/api/classDetail")
public class ClassDetailApiController_sangin {

    @Autowired
    private ClassDetailService_sangin classDetailService;

    @GetMapping("/review")
    public ResponseEntity<List<ClassReviewDTO>> getClassReviews(@RequestParam("classId") Integer classId) {
        if (classId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<ClassReviewDTO> reviews = classDetailService.getReviewsByClassId(classId);

        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/introduce")
    public ResponseEntity<List<ClassIntroduceDTO>> getClassIntroduces(@RequestParam("classId") Integer classId) {
        if (classId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<ClassIntroduceDTO> introduces = classDetailService.getIntroducesByClassId(classId);
        if (introduces.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(introduces);
    }

    @PostMapping("/review")
    public ResponseEntity<String> addClassReview(@RequestBody ClassReviewDTO classReviewDTO) {
        if (classReviewDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        classReviewDTO.setUserId(SecurityUtil.getCurrentUserId());
        if (classDetailService.postReview(classReviewDTO) == 1) {
            return ResponseEntity.ok().body("성공적으로 등록되었습니다");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
