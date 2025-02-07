package com.example.greenlast.controllers.views.kwanhyun;

import com.example.greenlast.dto.CommunityPostDTO;
import com.example.greenlast.security.SecurityUtil;
import com.example.greenlast.service.kwanhyun.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created on 2025-01-24 by 노관현
 * 커뮤니티 관련 컨트롤러입니다.
 */
@Controller
@RequestMapping("/kwanhyun/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/CommunityMain")
    public String communityMain(@RequestParam(defaultValue = "1") int page, Model model) {
        List<CommunityPostDTO> communityPostList = communityService.CommunityPostList(page);

        int totalPosts = communityService.getTotalPostCount();
        int totalPages = (int) Math.ceil((double) totalPosts / 10);

        if (totalPages < 1) totalPages = 1;

        model.addAttribute("communityPostList", communityPostList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "kwanhyun/CommunityMain";
    }

    @GetMapping("/CommunityRegist")
    public String communityRegist() {
        return "kwanhyun/CommunityRegist";
    }

    @GetMapping("/CommunityDetail")
    public String communityDetail(@RequestParam("postId") int postId, Model model) {
        CommunityPostDTO post = communityService.getCommunityPost(postId);
        String currentUserId = null;
        try {
            currentUserId = SecurityUtil.getCurrentUserId();
        } catch (Exception e) {
            log.info("로그인하지 않은 사용자 접근");
        }

        model.addAttribute("communityPost", post);
        model.addAttribute("currentUserId", currentUserId);

        return "kwanhyun/CommunityDetail";
    }

    @GetMapping("/CommunityEdit")
    public String communityEdit(@RequestParam("postId") int postId, Model model) {
        CommunityPostDTO communityPost = communityService.getCommunityPost(postId);
        String currentUserId = SecurityUtil.getCurrentUserId();

        if (!communityPost.getUserId().equals(currentUserId)) {
            return "redirect:/kwanhyun/community/CommunityMain?error=unauthorized";
        }

        model.addAttribute("communityPost", communityPost);
        return "kwanhyun/CommunityEdit";
    }



    @GetMapping("/CommunitySearch")
    public String communitySearch() {

        return "kwanhyun/CommunitySearch";
    }

}
