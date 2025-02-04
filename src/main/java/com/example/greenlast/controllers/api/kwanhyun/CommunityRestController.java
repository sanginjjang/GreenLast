package com.example.greenlast.controllers.api.kwanhyun;

import com.example.greenlast.dto.CommunityPostDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.kwanhyun.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2025-01-24 by 노관현
 * 커뮤니티 API 컨트롤러입니다.
 */
@RestController
@RequestMapping("/kwanhyun/api/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityRestController {

    private final CommunityService communityService;


    // 커뮤니티 게시글 목록 API
    @GetMapping("/posts")
    public List<CommunityPostDTO> getCommunityPost(@RequestParam(defaultValue = "1") int page) {
        return communityService.CommunityPostList(page);
    }

    // 커뮤니티 게시글 등록 API
    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestBody CommunityPostDTO communityPostDto) {
        communityPostDto.setUserId(SecurityUtil.getCurrentUserId());
        communityPostDto.setCategory("U");
        communityService.regCommunityPost(communityPostDto);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@restcontroller" + communityPostDto);
        return ResponseEntity.ok("게시글이 성공적으로 저장되었습니다.");
    }

    // 특정 게시글 조회 API
    @GetMapping("/post/{postId}")
    public CommunityPostDTO getPostById(@PathVariable int postId) {
        return communityService.getCommunityPost(postId);
    }
}
