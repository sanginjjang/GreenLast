package com.example.greenlast.dao.joontaek;


import com.example.greenlast.dto.ClassManagementDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassManagementDao {

    public List<ClassManagementDTO> getClassManagementInfo(String userId);
}
