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

    public List<CommunityPostDTO> CommunityPostList(int page) {
        int limit = 10;
        int offset = (page - 1) * limit;
        List<CommunityPostDTO> communityNoticeList = communityDao.getCommunityNoticeList();
        List<CommunityPostDTO> communityPostList = communityDao.getCommunityPostList(offset, limit);
        communityNoticeList.addAll(communityPostList);

        if (communityNoticeList.isEmpty()) {
            log.info("게시글이 존재하지 않습니다.");
            return null;
        }
        System.out.println(communityNoticeList);

        return communityNoticeList;
    }

    public CommunityPostDTO getCommunityPost(int postId) {
        CommunityPostDTO communityPostDetail = communityDao.getCommunityPost(postId);

        if(communityPostDetail == null) {
            return null;
        }
        System.out.println(communityPostDetail);

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

    public int getTotalPostCount() {
        return communityDao.getTotalPostCount();
    }

}
