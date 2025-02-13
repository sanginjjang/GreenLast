package com.example.greenlast.dao.sangin;

import com.example.greenlast.dto.ClassIntroduceDTO;
import com.example.greenlast.dto.ClassReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassDetailDao_sangin {
    public List<ClassReviewDTO> getReviewsByClassId(Integer classId);
    public List<ClassIntroduceDTO> getIntroducesByClassId(Integer classId);
    public int postReview(@Param("review") ClassReviewDTO reviewDto);
    public int postReviewStatus(@Param("review") ClassReviewDTO reviewDto);
//    public List<ClassIntroduceDTO> getIntroducesByClassId(Integer classId);
}
