package com.example.greenlast.controllers.api.sangin;

import com.example.greenlast.dto.ClassReviewDTO;
import com.example.greenlast.dto.CommunityCommentDTO;
import com.example.greenlast.dto.CommunityPostDTO;
import com.example.greenlast.dto.UserDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.sangin.MyPageService_sangin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2025-01-31 by 한상인
 */
@RestController
@RequestMapping("/api/mypage")
public class MyPageApiController_sangin {

    @Autowired
    MyPageService_sangin myPageService;

    @GetMapping("/getUserById")
    public ResponseEntity<?> infoForm() {
        String userId = SecurityUtil.getCurrentUserId();
        String role = SecurityUtil.getCurrentUserRole();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("\uD83D\uDD34 userId  : " + userId);
        System.out.println("\uD83D\uDD34 role : " + role);
        UserDTO user = myPageService.getUserById(userId);
        if(user != null) {
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/modifyUser")
    public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO) {
        String userId = SecurityUtil.getCurrentUserId();
        userDTO.setUserId(userId);
        int result = myPageService.modifyUserById(userDTO);
        if(result == 1) {
            return ResponseEntity.ok(userDTO);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getWrittenBorad")
    public ResponseEntity<?> getWrittenBoard() {
        String userId = SecurityUtil.getCurrentUserId();
        List<CommunityPostDTO> boardList = myPageService.getCommunityPostByUserId(userId);
        if(boardList != null) {
            return ResponseEntity.ok(boardList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getWrittenComment")
    public ResponseEntity<?> getWrittenComment() {
        String userId = SecurityUtil.getCurrentUserId();
        List<CommunityCommentDTO> commentList = myPageService.getWrittenCommentByUserId(userId);
        if(commentList != null) {
            return ResponseEntity.ok(commentList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getWrittenReview")
    public ResponseEntity<?> getWrittenReview() {
        String userId = SecurityUtil.getCurrentUserId();
        List<ClassReviewDTO> reviewList = myPageService.getWrittenReviewByUserId(userId);
        if(reviewList != null) {
            return ResponseEntity.ok(reviewList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
