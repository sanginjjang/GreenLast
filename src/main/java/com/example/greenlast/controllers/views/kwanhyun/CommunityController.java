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
import org.springframework.web.bind.annotation.PathVariable;
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
    public String communityMain(@RequestParam(value = "pageType", required = false) String pageType,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                Model model) {

        List<CommunityPostDTO> postList = communityService.CommunityPostList(page, search, keyword, pageType);
        int totalPosts = communityService.getTotalPostCount(search, keyword, pageType);
        int totalPages = (int) Math.ceil((double) totalPosts / 10);
        String currentUserRole = SecurityUtil.getCurrentUserRole();

        model.addAttribute("postList", postList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageType", pageType);
        model.addAttribute("currentUserRole", currentUserRole);

        System.out.println("\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34page" + totalPages);
        System.out.println("\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34post" + totalPosts);

        return "kwanhyun/CommunityMain";
    }


    @GetMapping("/CommunityRegister")
    public String communityRegister(@RequestParam(value = "pageType", required = false) String pageType,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    Model model) {
        model.addAttribute("pageType", pageType);
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        model.addAttribute("keyword", keyword);
        return "kwanhyun/CommunityRegister";
    }


    @GetMapping("/CommunityDetail")
    public String communityDetail(@RequestParam("postId") int postId,
                                  @RequestParam(value = "pageType", required = false) String pageType,
                                  Model model) {
        communityService.viewCounter(postId);

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

        model.addAttribute("communityPost", post);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("pageType", pageType);

        return "kwanhyun/CommunityDetail";
    }


    @GetMapping("/CommunityEdit")
    public String communityEdit(@RequestParam("postId") int postId,
                                @RequestParam(value = "pageType", required = false) String pageType,
                                Model model) {
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

        model.addAttribute("communityPost", post);
        model.addAttribute("pageType", pageType);

        return "kwanhyun/CommunityEdit";
    }

}
