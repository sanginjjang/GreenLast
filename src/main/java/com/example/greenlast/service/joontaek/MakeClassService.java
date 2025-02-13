package com.example.greenlast.service.joontaek;

import com.example.greenlast.dto.ClassDTO;

import java.util.List;

public interface MakeClassService {






    public int saveElement(int blockId, String type, String content);

    public int getBlockNum();

    public int saveBlock(int classId, String blockType);

    //강의 승인 구현중(동하)
    public List<ClassDTO> getPendingClasses();
    public int approveClass(int classId);
    public int rejectClass(int classId);
}
