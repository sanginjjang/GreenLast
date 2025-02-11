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
    public List<CommunityPostDTO> getCommunityPosts(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "keyword", required = false) String keyword,
                                                    @RequestParam(value = "pageType", required = false) String pageType) {
        return communityService.CommunityPostList(page, search, keyword, pageType);
    }

    // 커뮤니티 게시글 상세 API
    @GetMapping("/post")
    public CommunityPostDTO getCommunityPost(CommunityPostDTO communityPostDto) {
        return communityService.getCommunityPost(communityPostDto);
    }

    // 커뮤니티 게시글 등록 API
    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestBody CommunityPostDTO communityPostDto) {
        communityPostDto.setUserId(SecurityUtil.getCurrentUserId());
        String userRole = SecurityUtil.getCurrentUserRole();
        String pageType = communityPostDto.getPageType();

        if(userRole.contains("ROLE_ADMIN")) {
            if ("free".equals(pageType)) {
                communityPostDto.setCategory("N");
            } else if ("faq".equals(pageType)) {
                communityPostDto.setCategory("F");
            } else if ("qna".equals(pageType)) {
                communityPostDto.setCategory("Q");
            } else if ("class".equals(pageType)) {
                communityPostDto.setCategory("C");
            }
        }
        else if(userRole.contains("ROLE_USER")) {
            if ("free".equals(pageType)) {
                communityPostDto.setCategory("U");
            } else if ("qna".equals(pageType)) {
                communityPostDto.setCategory("Q");
            } else if ("class".equals(pageType)) {
                communityPostDto.setCategory("C");
            }
        }

        communityService.regCommunityPost(communityPostDto);
        return ResponseEntity.ok("게시글이 성공적으로 저장되었습니다.");
    }


    // 커뮤니티 게시글 수정 API
    @PutMapping("/post/{postId}")
    public String updateCommunityPost(@PathVariable int postId,
                                      @RequestParam(value = "pageType", required = false) String pageType,
                                      @RequestBody CommunityPostDTO communityPostDto) {
        CommunityPostDTO postDto = new CommunityPostDTO();
        postDto.setPostId(postId);

        CommunityPostDTO existingPost = communityService.getCommunityPost(postDto);
        String currentUserId = SecurityUtil.getCurrentUserId();

        if (!existingPost.getUserId().equals(currentUserId)) {
            return "redirect:/kwanhyun/community/CommunityMain/" + pageType + "?error=unauthorized";
        }

        existingPost.setTitle(communityPostDto.getTitle());
        existingPost.setContent(communityPostDto.getContent());
        communityService.updateCommunityPost(existingPost);

        return "redirect:/kwanhyun/community/CommunityMain/" + pageType;
    }


    // 커뮤니티 게시글 삭제 API
    @DeleteMapping("/post/{postId}")
    public String deleteCommunityPost(@PathVariable int postId,
                                      @RequestParam(value = "pageType", required = false) String pageType) {
        CommunityPostDTO postDto = new CommunityPostDTO();
        postDto.setPostId(postId);

        CommunityPostDTO post = communityService.getCommunityPost(postDto);
        String currentUserId = SecurityUtil.getCurrentUserId();


        if(post.getCategory().equals("U")) {
            pageType = "free";
        } else if(post.getCategory().equals("N")) {
            pageType = "free";
        } else if(post.getCategory().equals("Q")) {
            pageType = "qna";
        } else if(post.getCategory().equals("F")) {
            pageType = "faq";
        } else if(post.getCategory().equals("C")) {
            pageType = "class";
        }

        if (!post.getUserId().equals(currentUserId)) {
            return "redirect:/kwanhyun/community/CommunityMain/" + pageType + "?error=unauthorized";
        }

        communityService.deleteCommunityPost(postId);

        return "redirect:/kwanhyun/community/CommunityMain/" + pageType;
    }


}
