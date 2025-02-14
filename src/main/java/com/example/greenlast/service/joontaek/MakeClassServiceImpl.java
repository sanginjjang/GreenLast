package com.example.greenlast.service.joontaek;


import com.example.greenlast.dao.joontaek.MakeClassDao;
import com.example.greenlast.dto.ClassDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MakeClassServiceImpl implements MakeClassService {

    @Autowired
    MakeClassDao dao;

    @Override
    public int saveClassInfo(ClassDTO classDTO) {
        int result = dao.saveClassInfo(classDTO);
        return result;
    }

    @Override
    public int saveSection(int classId, String sectionTitle) {

        int result = dao.saveSection(classId, sectionTitle);
        return result;
    }

    @Override
    public int getSectionId() {
        int sectionId = dao.getSectionId();
        return sectionId;
    }

    @Override
    public int saveLesson(int sectionId, String lessonTitle) {
        int result = dao.saveLesson(sectionId, lessonTitle);
        return result;
    }

    @Override
    public int getLessonId() {
        int lessonId = dao.getLessonId();
        return lessonId;
    }

    @Override
    public int getFileNo() {
        int fileNo = dao.getFileNo();
        return fileNo;
    }

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
