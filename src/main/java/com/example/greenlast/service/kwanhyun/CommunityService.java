package com.example.greenlast.service.kwanhyun;

import com.example.greenlast.dao.kwanhyun.CommunityDao;
import com.example.greenlast.dto.CommunityPostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.util.List;

/*
 * Created on 2025-01-24 by 노관현
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    @Autowired
    CommunityDao communityDao;

    public List<CommunityPostDTO> CommunityPostList(int page, String search, String keyword, String pageType) {
        int limit = 10;
        int offset = (page - 1) * limit;

        List<CommunityPostDTO> postList;

        if ("free".equals(pageType)) {
            List<CommunityPostDTO> communityNoticeList = communityDao.getCommunityNoticeList();
            List<CommunityPostDTO> communityPostList = communityDao.getCommunityPostList(offset, limit, search, keyword, pageType);

            communityNoticeList.addAll(communityPostList);
            postList = communityNoticeList;
        } else {
            postList = communityDao.getCommunityPostList(offset, limit, search, keyword, pageType);
        }

        if (postList.isEmpty()) {
            log.info("게시글이 존재하지 않습니다.");
            return null;
        }

        return postList;
    }


    public CommunityPostDTO getCommunityPost(CommunityPostDTO communityPostDto) {
        CommunityPostDTO communityPostDetail = communityDao.getCommunityPost(communityPostDto);

        return communityPostDetail;
    }

    public void updateCommunityPost(CommunityPostDTO communityPostDto) {
        String cleanContent = Jsoup.parse(communityPostDto.getContent()).text();
        communityPostDto.setContent(cleanContent);

        communityDao.updateCommunityPost(communityPostDto);
    }

    public void deleteCommunityPost(int postId) {
        communityDao.deleteCommunityPost(postId);
    }

    public void regCommunityPost(CommunityPostDTO communityPostDto) {
        String cleanContent = Jsoup.parse(communityPostDto.getContent()).text();
        communityPostDto.setContent(cleanContent);

        communityDao.regCommunityPost(communityPostDto);
    }

    public void viewCounter(int postId) {
        communityDao.viewCounter(postId);
    }

    public int getTotalPostCount(String search, String keyword, String pageType) {
        if (pageType != null) {
            switch (pageType) {
                case "free":
                    pageType = "U";
                    break;
                case "qna":
                    pageType = "Q";
                    break;
                case "faq":
                    pageType = "F";
                    break;
                case "class":
                    pageType = "C";
                    break;
            }
        }
        return communityDao.getTotalPostCount(search, keyword, pageType);
    }

}
