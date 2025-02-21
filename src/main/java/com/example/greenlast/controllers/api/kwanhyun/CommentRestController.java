package com.example.greenlast.controllers.api.kwanhyun;

import com.example.greenlast.dto.CommunityCommentDTO;
import com.example.greenlast.dto.CommunityPostDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.kwanhyun.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2025-02-13 by 노관현
 * 커뮤니티 댓글 API 컨트롤러입니다.
 */
@RestController
@RequestMapping("/kwanhyun/api/community")
@RequiredArgsConstructor
@Slf4j
public class CommentRestController {


    private final CommentService commentService;


    // 커뮤니티 댓글 목록 API
    @GetMapping("/comments")
    public List<CommunityCommentDTO> getComments(@RequestParam int postId) {
        return commentService.CommunityCommentList(postId);
    }

    // 커뮤니티 댓글 등록 API
    @PostMapping("/comment")
    public ResponseEntity<String> registerComment(@RequestBody CommunityCommentDTO communityCommentDto) {
        communityCommentDto.setUserId(SecurityUtil.getCurrentUserId());
        String userRole = SecurityUtil.getCurrentUserRole();

        if(userRole.contains("ROLE_ADMIN")) {
            commentService.regComment(communityCommentDto);
            return ResponseEntity.ok("댓글이 성공적으로 등록되었습니다.");
        } else if(userRole.contains("ROLE_USER")) {
            commentService.regComment(communityCommentDto);
            return ResponseEntity.ok("댓글이 성공적으로 등록되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게스트는 작성할 수 없습니다.");
        }
    }

    // 커뮤니티 댓글 수정 API
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable int commentId,
                                                @RequestBody CommunityCommentDTO communityCommentDto) {
        CommunityCommentDTO existingComment = commentService.getComment(commentId);

        if (existingComment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다.");
        }

        String currentUserId = SecurityUtil.getCurrentUserId();
        if (!existingComment.getUserId().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인만 수정할 수 있습니다.");
        }

        existingComment.setContent(communityCommentDto.getContent());
        commentService.updateComment(existingComment);

        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }


    // 커뮤니티 게시글 삭제 API
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId) {
        CommunityCommentDTO commentDTO = commentService.getComment(commentId);

        if (commentDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다.");
        }

        String currentUserId = SecurityUtil.getCurrentUserId();
        if (!commentDTO.getUserId().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인만 삭제할 수 있습니다.");
        }

        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }

}
