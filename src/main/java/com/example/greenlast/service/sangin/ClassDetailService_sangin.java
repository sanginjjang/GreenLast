package com.example.greenlast.service.sangin;

import com.example.greenlast.dao.sangin.ClassDetailDao_sangin;
import com.example.greenlast.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2025-02-10 by 한상인
 */
@Service
public class ClassDetailService_sangin {
    @Autowired
    ClassDetailDao_sangin classDetailDao;

    public List<ClassReviewDTO> getReviewsByClassId(Integer classId) {
        return classDetailDao.getReviewsByClassId(classId);
    }

    public List<ClassIntroduceDTO> getIntroducesByClassId(Integer classId) {
        return classDetailDao.getIntroducesByClassId(classId);
    }

    public List<CommunityPostDTO> getClassCommunityByClassId(Integer classId) {
        return classDetailDao.getClassCommunityByClassId(classId);
    }

    public CommunityPostDTO getCommunityPostByPostId(Integer postId) {
        return classDetailDao.getCommunityPostByPostId(postId);
    }

    public List<CommunityCommentDTO> getCommunityCommentByPostId(Integer postId) {
        return classDetailDao.getCommunityCommentByPostId(postId);
    }

    public int postReview(ClassReviewDTO classReviewDTO) {
        classDetailDao.postReviewStatus(classReviewDTO);
        return classDetailDao.postReview(classReviewDTO);
    }

    public List<ClassSectionDTO> getCurriculumByClassId(Integer classId) {
        List<ClassSectionDTO> sections = classDetailDao.getSectionByClassId(classId);
        for (ClassSectionDTO section : sections) {
            List<ClassLessonDTO> lessons = classDetailDao.getLessonBySectionId(section.getSectionId());
            section.setLessonDTOList(lessons);
        }
        return sections;
    }

    //동하형 여기 introduce
    public List<IntroduceBlockDto> getIntroduceBlockByClassId(Integer classId) {
        List<IntroduceBlockDto> blocks = classDetailDao.getIntroduceBlockByClassId(classId);
        for (IntroduceBlockDto block : blocks) {
            List<BlockElementDto> elements = classDetailDao.getBlockElementByBlockId(block.getBlockId());
            block.setElements(elements);
        }
        return blocks;
    }
    //동하형 여기 introduce

}
