package com.example.greenlast.controllers.api.sangin;

import com.example.greenlast.dto.ClassReviewDTO;
import com.example.greenlast.dto.CommunityCommentDTO;
import com.example.greenlast.dto.CommunityPostDTO;
import com.example.greenlast.dto.UserDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.sangin.MyPageService_sangin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        System.out.println("/api/mypage/infoForm");
        String userId = SecurityUtil.getCurrentUserId();
        UserDTO user = myPageService.getUserById(userId);
        if(user != null) {
            System.out.println(user);
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/modifyUser")
    public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO) {
        System.out.println("/api/mypage/modifyUser" + userDTO);
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
        System.out.println("/api/mypage/getWrittenBoard");
        String userId = SecurityUtil.getCurrentUserId();
        List<CommunityPostDTO> boardList = myPageService.getCommunityPostByUserId(userId);
        if(boardList != null) {
            System.out.println("@@@@" + boardList);
            return ResponseEntity.ok(boardList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getWrittenComment")
    public ResponseEntity<?> getWrittenComment() {
        System.out.println("/api/mypage/getWrittenComment");
        String userId = SecurityUtil.getCurrentUserId();
        List<CommunityCommentDTO> commentList = myPageService.getWrittenCommentByUserId(userId);
        if(commentList != null) {
            System.out.println("@@@@" + commentList);
            return ResponseEntity.ok(commentList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getWrittenReview")
    public ResponseEntity<?> getWrittenReview() {
        System.out.println("/api/mypage/getWrittenReview");
        String userId = SecurityUtil.getCurrentUserId();
        List<ClassReviewDTO> reviewList = myPageService.getWrittenReviewByUserId(userId);
        if(reviewList != null) {
            System.out.println("@@@@" + reviewList);
            return ResponseEntity.ok(reviewList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
