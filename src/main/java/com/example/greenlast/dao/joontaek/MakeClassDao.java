package com.example.greenlast.dao.joontaek;

import com.example.greenlast.dto.ClassDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MakeClassDao {

    public int getMaxClassId();








    public int saveElement(int blockId, String type, String content);

    public int getBlockNum();

    public int saveBlock(int classId, String blockType);

    //강의 승인을 위한 전체 조회 메서드
    public List<ClassDTO> getPendingClasses();
    public int approveClass(int classId);
    public int rejectClass(int classId);
}
