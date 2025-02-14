package com.example.greenlast.dao.joontaek;

import com.example.greenlast.dto.ClassDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MakeClassDao {

    public int getMaxClassId();

    public int saveClassInfo(ClassDTO classDTO);



    public int saveSection(int classId, String sectionTitle);
    public int getSectionId();
    public int saveLesson(int sectionId, String lessonTitle);

    public int getLessonId();

    public int getFileNo();



    public int saveElement(int blockId, String type, String content);

    public int getBlockNum();

    public int saveBlock(int classId, String blockType);
}
