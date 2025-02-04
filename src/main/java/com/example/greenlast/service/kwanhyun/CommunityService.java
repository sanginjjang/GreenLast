package com.example.greenlast.service.kwanhyun;

import com.example.greenlast.dao.kwanhyun.CommunityDao;
import com.example.greenlast.dto.CommunityPostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<CommunityPostDTO> CommunityPostList() {
        List<CommunityPostDTO> communitynoticeList = communityDao.getCommunityNoticeList();
        communitynoticeList.addAll(communityDao.getCommunityPostList());

        if (communitynoticeList.isEmpty()) {
            log.info("게시글이 존재하지 않습니다.");
            return null;
        }
        System.out.println(communitynoticeList);

        return communitynoticeList;
    }

    public CommunityPostDTO getCommunityPost(int postId) {
        CommunityPostDTO communityPostDetail = communityDao.getCommunityPost(postId);

        if(communityPostDetail == null) {
            return null;
        }
        System.out.println(communityPostDetail);

        return communityPostDetail;
    }

    public CommunityPostDTO registCommunityPost(CommunityPostDTO communityPostDto) {
        CommunityPostDTO communityPostDTO = communityDao.regCommunityPost(communityPostDto);
        return communityPostDTO;
    }
}
