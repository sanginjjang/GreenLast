package com.example.greenlast.dao.kwanhyun;

import com.example.greenlast.dto.CommunityPostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on 2025-01-22 by 노관현
 */

@Mapper
public interface CommunityDao {
    public void regCommunityPost(CommunityPostDTO communityPostdto);
    public CommunityPostDTO getCommunityPost(CommunityPostDTO communityPostDto);
    public List<CommunityPostDTO> getCommunityNoticeList();
    public List<CommunityPostDTO> getCommunityPostList(    @Param("offset") int offset,
                                                           @Param("limit") int limit,
                                                           @Param("search") String search,
                                                           @Param("keyword") String keyword,
                                                           @Param("pageType") String pageType);
    public void updateCommunityPost(CommunityPostDTO communityPost);
    public void deleteCommunityPost(int postId);
    public int getTotalPostCount(@Param("search") String search, @Param("keyword") String keyword, @Param("pageType") String pageType);
    public void viewCounter(int postId);
}