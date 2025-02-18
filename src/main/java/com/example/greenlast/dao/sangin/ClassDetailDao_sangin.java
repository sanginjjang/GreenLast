package com.example.greenlast.dao.sangin;

import com.example.greenlast.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    //동하형 여기 introduce
    public List<IntroduceBlockDto> getIntroduceBlockByClassId(int classId);
    public List<BlockElementDto> getBlockElementByBlockId(int blockId);
    //동하형 여기 introduce



}
