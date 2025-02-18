package com.example.greenlast.service.joontaek;

import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.FileDTO;

public interface UpdateClassService {


    public ClassDTO getClassInfo(int classId);
    public FileDTO getThumbnailImg(int fileNo);
    public int updateClassInfo(ClassDTO classDTO);
}
