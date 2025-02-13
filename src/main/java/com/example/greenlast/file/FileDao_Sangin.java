package com.example.greenlast.file;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileDao_Sangin {

    // ✅ 파일 업로드 시 참조 ID 저장
    int insertIntroduce(@Param("introduceId") int introduceId, @Param("refNo") int refNo);
    int insertPost(@Param("postId") int postId, @Param("refNo") int refNo);
    int insertThumbnail(@Param("classId") int classId, @Param("refNo") int refNo);
    int updateProfile(@Param("userId") String userId, @Param("refNo") int refNo);

    int updateIntroduce(@Param("introduceId") int introduceId, @Param("refNo") int refNo);
    int updatePost(@Param("postId") int postId, @Param("refNo") int refNo);
    int updateThumbnail(@Param("classId") int classId, @Param("refNo") int refNo);

    // ✅ 참조 데이터 삭제 메서드 추가
    int deleteIntroduceRef(@Param("fileNo") int fileNo);
    int deletePostRef(@Param("fileNo") int fileNo);
    int deleteThumbnailRef(@Param("fileNo") int fileNo);
    int deleteProfileRef(@Param("fileNo") int fileNo);
}
