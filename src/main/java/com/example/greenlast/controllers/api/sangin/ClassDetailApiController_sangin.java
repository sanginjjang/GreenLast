package com.example.greenlast.controllers.api.sangin;

import com.example.greenlast.dto.*;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.sangin.ClassDetailService_sangin;
import org.apache.coyote.Response;
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

    //동하형 여기 introduce 시작
    @GetMapping("/introduce")
    public ResponseEntity<List<IntroduceBlockDto>> getIntroduceBlockByClassId(@RequestParam("classId") Integer classId) {
        if (classId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<IntroduceBlockDto> blocks = classDetailService.getIntroduceBlockByClassId(classId);
        if (blocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(blocks);
    }
    //동하형 여기 introduce 끝

    @GetMapping("/community")
    public ResponseEntity<List<CommunityPostDTO>> getClassCommnuity(@RequestParam("classId") Integer classId) {
        if (classId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CommunityPostDTO> posts = classDetailService.getClassCommunityByClassId(classId);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        System.out.println("posts" + posts);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/communityDetail")
    public ResponseEntity<CommunityPostDTO> getCommunityPostByClassId(@RequestParam("postId") Integer postId) {
        if (postId == null) {
            return ResponseEntity.badRequest().build();
        }
        CommunityPostDTO post = classDetailService.getCommunityPostByPostId(postId);
        if (post == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/communityComments")
    public ResponseEntity<List<CommunityCommentDTO>> getCommunityCommentByPostId(@RequestParam("postId") Integer postId) {
        if (postId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<CommunityCommentDTO> comments = classDetailService.getCommunityCommentByPostId(postId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
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

    @GetMapping("/curriculum")
    public ResponseEntity<List<ClassSectionDTO>> getCurriculumByClassId(@RequestParam("classId") Integer classId) {
        if (classId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ClassSectionDTO> sections = classDetailService.getCurriculumByClassId(classId);
        if (sections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        System.out.println(sections);
        return ResponseEntity.ok(sections);
    }
}
