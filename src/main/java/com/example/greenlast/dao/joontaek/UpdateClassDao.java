package com.example.greenlast.dao.joontaek;


import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UpdateClassDao {

    public ClassDTO getClassInfo(int classId);
    public FileDTO getThumbnailImg(int fileNo);

    public int updateClassInfo(ClassDTO classDTO);
}
