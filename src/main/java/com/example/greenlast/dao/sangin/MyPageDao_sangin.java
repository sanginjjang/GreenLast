package com.example.greenlast.dao.sangin;

import com.example.greenlast.dto.ClassReviewDTO;
import com.example.greenlast.dto.CommunityCommentDTO;
import com.example.greenlast.dto.CommunityPostDTO;
import com.example.greenlast.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created on 2025-01-31 by 한상인
 */
@Mapper
public interface MyPageDao_sangin {
    public UserDTO getUserById(String userId);
    public int modifyUser(UserDTO userDTO);
    public List<CommunityPostDTO> getCommunityPostByUserId(String userId);
    public List<CommunityCommentDTO> getWrittenCommentByUserId(String userId);
    public List<ClassReviewDTO> getWrittenReviewByUserId(String userId);
}
