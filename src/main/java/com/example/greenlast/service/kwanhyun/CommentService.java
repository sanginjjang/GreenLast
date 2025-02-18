package com.example.greenlast.service.kwanhyun;

import com.example.greenlast.dao.kwanhyun.CommentDao;
import com.example.greenlast.dto.CommunityCommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Created on 2025-02-13 by 노관현
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentDao commentDao;


    public void regComment(CommunityCommentDTO CommunityCommentDto) {

        commentDao.regComment(CommunityCommentDto);
    }

    public List<CommunityCommentDTO> CommunityCommentList(int postId) {
        List<CommunityCommentDTO> commentList = commentDao.getCommentList(postId);

        if (commentList.isEmpty()) {
            log.info("댓글이 존재하지 않습니다.");
            return null;
        }

        return commentList;
    }

    public CommunityCommentDTO getComment(int commentId) {
        return commentDao.getComment(commentId);
    }

    public void updateComment(CommunityCommentDTO communityCommentDto) {

        commentDao.updateComment(communityCommentDto);
    }

    public void deleteComment(int commentId) {
        commentDao.deleteComment(commentId);
    }

    public int getTotalCommentCount(int postId) {

        return commentDao.getTotalCommentCount(postId);
    }
}
