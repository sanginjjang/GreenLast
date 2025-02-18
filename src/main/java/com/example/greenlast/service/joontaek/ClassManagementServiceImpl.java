package com.example.greenlast.service.joontaek;

import com.example.greenlast.dao.joontaek.ClassManagementDao;
import com.example.greenlast.dto.ClassManagementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassManagementServiceImpl implements ClassManagementService {

    @Autowired
    ClassManagementDao dao;

    @Override
    public List<ClassManagementDTO> getClassManagementInfo(String userId) {
        List<ClassManagementDTO> classManagements = dao.getClassManagementInfo(userId);
        return classManagements;
    }
}
