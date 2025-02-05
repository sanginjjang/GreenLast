package com.example.greenlast.dao.joontaek;


import com.example.greenlast.dto.AgeGraphDTO;
import com.example.greenlast.dto.RevenueGraphDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassDashboardDao {

    public int allStudentCnt(String userId);

    public int allClassCnt(String userId);

    public String allRevenue(String userId);

    public int newStudentCnt(String userId);

    public double ratingAvg(String userId);


    // REST@@@@@@@@@@@@@@@@
    public List<RevenueGraphDTO> getRevenueGraph(String userId);

    public List<AgeGraphDTO> getAgeGraph(String userId);
}
