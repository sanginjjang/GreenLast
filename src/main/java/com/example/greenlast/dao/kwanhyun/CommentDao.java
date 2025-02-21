package com.example.greenlast.dao.kwanhyun;

import com.example.greenlast.dto.CommunityCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created on 2025-02-13 by 노관현
 */

@Mapper
public interface CommentDao {
    public void regComment(CommunityCommentDTO communitycommentDto);
    public List<CommunityCommentDTO> getCommentList(int postId);
    public CommunityCommentDTO getComment(int commentId);
    public void updateComment(CommunityCommentDTO communitycommentDto);
    public void deleteComment(int commentId);
    public int getTotalCommentCount(int postId);
}
