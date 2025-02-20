package com.example.greenlast.dao.sangin;

import com.example.greenlast.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface ClassDetailDao_sangin {
    public List<ClassReviewDTO> getReviewsByClassId(Integer classId);

    public List<ClassIntroduceDTO> getIntroducesByClassId(Integer classId);

    public List<CommunityPostDTO> getClassCommunityByClassId(Integer classId);

    public int postReview(@Param("review") ClassReviewDTO reviewDto);

    public int postReviewStatus(@Param("review") ClassReviewDTO reviewDto);

    public CommunityPostDTO getCommunityPostByPostId(Integer postId);

    public List<CommunityCommentDTO> getCommunityCommentByPostId(Integer postId);

    public List<ClassSectionDTO> getSectionByClassId(Integer classId);

    public List<ClassLessonDTO> getLessonBySectionId(Integer sectionId);

    public int postQuestionByClassId(@Param("classId") Integer classId
            , @Param("userId") String userId
            , @Param("title") String title
            , @Param("content") String content);

    public int postCommentByPostId(@Param("postId") Integer postId
            , @Param("userId") String userId
            , @Param("content") String content);

    //동하형 여기 introduce
    public List<IntroduceBlockDto> getIntroduceBlockByClassId(int classId);

    public List<BlockElementDto> getBlockElementByBlockId(int blockId);
    //동하형 여기 introduce

    public int updatePost(@Param("postId") Integer postId, @Param("title") String title, @Param("content") String content);

    public int deletePost(Integer postId);

    int updateComment(@Param("commentId") Integer commentId, @Param("content") String content);

    int deleteComment(@Param("commentId") Integer commentId);

}
