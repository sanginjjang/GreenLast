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
        System.out.println(comments);
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

    @PostMapping("/question")
    public ResponseEntity<String> postQuestionByClassId(@RequestParam("classId") Integer classId,
                                                        @RequestParam("title") String title,
                                                        @RequestParam("content") String content) {
        if (classId == null) {
            return ResponseEntity.badRequest().build();
        }
        if (title == null || content == null) {
            return ResponseEntity.badRequest().build();
        }
        String userId = SecurityUtil.getCurrentUserId();
        if (classDetailService.postQuestionByClassId(classId, userId, title, content) != 1) {
            return ResponseEntity.badRequest().body("등록이 올바로 이루어지지 않았습니다.");
        }
        return ResponseEntity.ok("질문이 등록되었습니다.");
    }

    @PostMapping("/comment")
    public ResponseEntity<String> postCommentByPostId(@RequestParam("postId") Integer postId,
                                                      @RequestParam("content") String content) {
        if (postId == null) {
            return ResponseEntity.badRequest().body("포스트 아이디가 없습니다");
        }
        if (content == null) {
            return ResponseEntity.badRequest().body("내용이 없습니다");
        }
        String userId = SecurityUtil.getCurrentUserId();
        if (classDetailService.postCommentByPostId(postId, userId, content) != 1) {
            return ResponseEntity.badRequest().body("등록이 올바로 이루어지지 않았습니다.");
        }
        return ResponseEntity.ok("댓글이 등록되었습니다.");
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
        return ResponseEntity.ok(sections);
    }

    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable("postId") Integer postId,
                                             @RequestBody CommunityPostDTO postDTO) {  // ✅ JSON body를 받도록 변경
        if (postDTO.getTitle() == null || postDTO.getContent() == null) {
            return ResponseEntity.badRequest().body("제목과 내용을 입력해주세요.");
        }

        int result = classDetailService.updatePost(postId, postDTO.getTitle(), postDTO.getContent());

        if (result != 1) {
            return ResponseEntity.badRequest().body("게시글 수정에 실패했습니다.");
        }

        return ResponseEntity.ok("게시글이 수정되었습니다.");
    }


    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Integer postId) {
        if (postId == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }

        int result = classDetailService.deletePost(postId);
        if (result == 1) {
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("게시글 삭제 중 오류가 발생했습니다.");
        }
    }

    @PutMapping("/updateComment/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("commentId") Integer commentId,
                                                @RequestBody CommunityPostDTO postDTO) {
        if (commentId == null || postDTO.getContent() == null || postDTO.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        int result = classDetailService.updateComment(commentId, postDTO.getContent());

        if (result != 1) {
            return ResponseEntity.badRequest().body("댓글 수정 실패");
        }

        return ResponseEntity.ok("댓글이 수정되었습니다.");
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Integer commentId) {
        if (commentId == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }

        int result = classDetailService.deleteComment(commentId);

        if (result != 1) {
            return ResponseEntity.badRequest().body("댓글 삭제 실패");
        }

        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }


}
