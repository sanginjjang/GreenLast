package com.example.greenlast.service.joontaek;

import com.example.greenlast.dto.ClassManagementDTO;

import java.util.List;

public interface ClassManagementService {

    public List<ClassManagementDTO> getClassManagementInfo(String userId);
}
