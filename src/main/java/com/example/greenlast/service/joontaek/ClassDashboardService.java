package com.example.greenlast.service.joontaek;

import com.example.greenlast.dto.AgeGraphDTO;
import com.example.greenlast.dto.RevenueGraphDTO;

import java.util.List;

public interface ClassDashboardService {

    public int allStudentCnt(String userId);

    public int allClassCnt(String userId);

    public String allRevenue(String userId);

    public int newStudentCnt(String userId);

    public double ratingAvg(String userId);

    //REST@@@@@@@@@@@

    public List<RevenueGraphDTO> getRevenueGraph(String userId);

    public List<AgeGraphDTO> getAgeGraph(String userId);


}
