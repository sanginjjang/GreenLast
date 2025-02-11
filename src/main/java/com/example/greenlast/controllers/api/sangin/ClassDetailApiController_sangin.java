package com.example.greenlast.controllers.api.sangin;

import com.example.greenlast.dto.ClassReviewDTO;
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

    @GetMapping("/")
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
}
