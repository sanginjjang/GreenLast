package com.example.greenlast.service.joontaek;


import com.example.greenlast.dao.joontaek.UpdateClassDao;
import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateClassServiceImpl implements UpdateClassService{

    @Autowired
    UpdateClassDao dao;

    @Override
    public ClassDTO getClassInfo(int classId) {

        ClassDTO classInfo = dao.getClassInfo(classId);

        return classInfo;
    }

    @Override
    public FileDTO getThumbnailImg(int fileNo) {
        FileDTO fileInfo = dao.getThumbnailImg(fileNo);
        return fileInfo;
    }

    @Override
    public int updateClassInfo(ClassDTO classDTO) {
        int result = dao.updateClassInfo(classDTO);
        return result;
    }
}
