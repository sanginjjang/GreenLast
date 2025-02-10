package com.example.greenlast.service.sangin;

import com.example.greenlast.dao.sangin.MyPageDao_sangin;
import com.example.greenlast.dto.ClassReviewDTO;
import com.example.greenlast.dto.CommunityCommentDTO;
import com.example.greenlast.dto.CommunityPostDTO;
import com.example.greenlast.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2025-01-31 by 한상인
 */
@Service
public class MyPageService_sangin {
    @Autowired
    MyPageDao_sangin myPageDao;

    public UserDTO getUserById(String userId) {
        return myPageDao.getUserById(userId);
    }
    public int modifyUserById(UserDTO userDTO) {
        return myPageDao.modifyUser(userDTO);
    }
    public List<CommunityPostDTO> getCommunityPostByUserId(String userId) {
        return myPageDao.getCommunityPostByUserId(userId);
    }
    public List<CommunityCommentDTO> getWrittenCommentByUserId(String userId) {
        return myPageDao.getWrittenCommentByUserId(userId);
    }
    public List<ClassReviewDTO> getWrittenReviewByUserId(String userId) {
        return myPageDao.getWrittenReviewByUserId(userId);
    }
}
