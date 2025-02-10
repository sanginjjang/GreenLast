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

    @GetMapping("/CommunityMain/{pageType}")
    public String communityMain(@PathVariable String pageType,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                Model model) {

        List<CommunityPostDTO> postList = communityService.CommunityPostList(page, search, keyword, pageType);
        int totalPosts = communityService.getTotalPostCount(search, keyword, pageType);
        int totalPages = (int) Math.ceil((double) totalPosts / 10);

        model.addAttribute("postList", postList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageType", pageType);

        return "kwanhyun/CommunityMain";
    }

    @GetMapping("/CommunityRegister/{pageType}")
    public String communityRegister(@PathVariable String pageType, Model model) {
        model.addAttribute("pageType", pageType);
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

        model.addAttribute("communityPost", post);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("pageType", pageType);

        return "kwanhyun/CommunityDetail";
    }


    @GetMapping("/CommunityEdit/{pageType}")
    public String communityEdit(@RequestParam("postId") int postId,
                                @RequestParam(value = "pageType", required = false) String pageType,
                                Model model) {
        CommunityPostDTO postDto = new CommunityPostDTO();
        postDto.setPostId(postId);

        CommunityPostDTO communityPost = communityService.getCommunityPost(postDto);
        String currentUserId = SecurityUtil.getCurrentUserId();

        if (!communityPost.getUserId().equals(currentUserId)) {
            return "redirect:/kwanhyun/community/CommunityMain/" + pageType + "?error=unauthorized";
        }

        model.addAttribute("communityPost", communityPost);
        model.addAttribute("pageType", pageType);

        return "kwanhyun/CommunityEdit";
    }


}
