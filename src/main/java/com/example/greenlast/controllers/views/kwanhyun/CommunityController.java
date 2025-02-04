package com.example.greenlast.controllers.views.kwanhyun;

import com.example.greenlast.dto.CommunityPostDTO;
import com.example.greenlast.service.kwanhyun.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String communityMain(Model model) {
        List<CommunityPostDTO> communityPostList = communityService.CommunityPostList();
        model.addAttribute("communitypostList", communityPostList);
        return "kwanhyun/CommunityMain";
    }

    @GetMapping("/CommunityRegist")
    public String communityRegist() {

        return "kwanhyun/CommunityRegist";
    }

    @GetMapping("/CommunityDetail")
    public String communityDetail() {

        return "kwanhyun/CommunityDetail";
    }

    @GetMapping("/CommunitySearch")
    public String communitySearch() {

        return "kwanhyun/CommunitySearch";
    }

}
