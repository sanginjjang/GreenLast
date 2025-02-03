package com.example.greenlast.service.dongha;

import com.example.greenlast.dao.dongha.IClassMainDao;
import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.ClassDetailDTO;
import com.example.greenlast.dto.ClassMainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.greenlast.service.dongha
 * fileName       : ClassMainService
 * author         : 이동하
 * date           : 25. 1. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 25.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class ClassMainService {
    private final IClassMainDao classMainDao;

    public List<ClassMainDTO> getClassMain(){
        return classMainDao.findClassMain();
    }
    public List<ClassMainDTO> getClassMainByKeyword(String keyword){
        return classMainDao.findClassMainByKeyword(keyword);
    }

    public ClassDetailDTO getClassDetail(Long id){
        return classMainDao.findClassDetail(id);
    }
}
