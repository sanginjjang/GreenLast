package com.example.greenlast.service.joontaek;


import com.example.greenlast.dao.joontaek.MakeClassDao;
import com.example.greenlast.dto.ClassDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MakeClassServiceImpl implements MakeClassService {

    @Autowired
    MakeClassDao dao;

    @Override
    public int saveElement(int blockId, String type, String content) {
        int result = dao.saveElement(blockId, type, content);

        return result;
    }

    @Override
    public int getBlockNum() {
        int blockId = dao.getBlockNum();
        return blockId;
    }

    @Override
    public int saveBlock(int classId, String blockType) {

        int result = dao.saveBlock(classId, blockType);

        return result;
    }

    @Override
    public List<ClassDTO> getPendingClasses() {
        return dao.getPendingClasses();
    }

    @Override
    public int approveClass(int classId) {
        return dao.approveClass(classId);
    }

    @Override
    public int rejectClass(int classId) {
        return dao.rejectClass(classId);
    }
}
