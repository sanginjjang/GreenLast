package com.example.greenlast.dao.joontaek;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MakeClassDao {

    public int getMaxClassId();








    public int saveElement(int blockId, String type, String content);

    public int getBlockNum();

    public int saveBlock(int classId, String blockType);
}
