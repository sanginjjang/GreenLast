package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.IPermitClassDao;
import com.example.greenlast.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.example.greenlast.service.dongha
 * fileName       : PermitClassService
 * author         : 이동하
 * date           : 25. 2. 14.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 14.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class PermitClassService {
    private final IPermitClassDao permitClassDao;

    public List<ClassDTO> getPendingClasses() {
        return permitClassDao.getPendingClasses();
    }
    public int approveReason(int classId) {
        return permitClassDao.approveClass(classId);
    }
    public int insertRejectReason(int classId, String reason) {
        return permitClassDao.insertRejectReason(classId, reason);
    }
    public ClassDTO getClassDetail(int classId) {
        return permitClassDao.getClassDetail(classId);
    }
    public int rejectClass(int classId, String reason) {
        return permitClassDao.rejectClass(classId, reason);
    }
    public List<ClassSectionDTO> getClassCurriculum(int classId) {
        List<ClassSectionDTO> sections = permitClassDao.getSectionByClassId(classId);
        for (ClassSectionDTO section : sections) {
            List<ClassLessonDTO> lessonDTOList = permitClassDao.getLessonsBySectionId(section.getSectionId());
            section.setLessonDTOList(lessonDTOList);
        }
        return sections;
    }
    public List<IntroduceBlockDto> getBlocksByClassId(int classId) {
        List<BlockElementDto> rawData = permitClassDao.getBlocksByClassId(classId);

        Map<Integer, IntroduceBlockDto> blockMap = new HashMap<>();

        for (BlockElementDto element : rawData) {
            int blockId = element.getBlockId();

            blockMap.putIfAbsent(blockId, new IntroduceBlockDto());
            IntroduceBlockDto block = blockMap.get(blockId);

            block.setBlockId(blockId);
            block.setClassId(classId);
            block.setBlockType(element.getBlockType());

            // elements 리스트가 없으면 초기화
            if (block.getElements() == null) {
                block.setElements(new ArrayList<>());
            }

            // 요소 추가
            if (element.getElementId() > 0) { // `NULL` 값 방지
                block.getElements().add(element);
            }
        }

        return new ArrayList<>(blockMap.values());
    }
    public List<BlockElementDto> getElementsByBlockId(int blockId) {
        List<BlockElementDto> elements = permitClassDao.getElementsByBlockId(blockId);

        for (BlockElementDto element : elements) {
            if ("image".equals(element.getElementType())) {
                String content = element.getElementContent();

                if (content.startsWith("C:/classInfoImg/")) {
                    content = content.replace("C:/classInfoImg/", "/upload/images/");
                }

                element.setElementContent(content); // 변환된 경로 저장
            }
        }
        return elements;
    }
}


