package com.example.greenlast.service.joontaek;


import com.example.greenlast.dao.joontaek.MakeClassDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
