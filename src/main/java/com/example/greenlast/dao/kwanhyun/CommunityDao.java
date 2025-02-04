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
    public CommunityPostDTO regCommunityPost(CommunityPostDTO communityPost);
    public CommunityPostDTO getCommunityPost(int postId);
    public List<CommunityPostDTO> getCommunityNoticeList();
    public List<CommunityPostDTO> getCommunityPostList(@Param("offset") int offset, @Param("limit") int limit);
    public List<CommunityPostDTO> searchCommunityPostList();
    public CommunityPostDTO updateCommunityPost(CommunityPostDTO communityPost);
    public void deleteCommunityPost(int postId);
    public int getTotalPostCount();

}