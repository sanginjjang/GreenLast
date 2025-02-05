package com.example.greenlast.service.joontaek;


import com.example.greenlast.dao.joontaek.ClassDashboardDao;
import com.example.greenlast.dto.AgeGraphDTO;
import com.example.greenlast.dto.RevenueGraphDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassDashboardServiceImpl implements ClassDashboardService {

    @Autowired
    private ClassDashboardDao dao;

    @Override
    public int allStudentCnt(String userId) {
        int allStudentCnt = dao.allStudentCnt(userId);
        return allStudentCnt;
    }

    @Override
    public int allClassCnt(String userId) {
        int allClassCnt = dao.allClassCnt(userId);
        return allClassCnt;
    }

    @Override
    public String allRevenue(String userId) {
        String allRevenue = dao.allRevenue(userId);
        return allRevenue;
    }

    @Override
    public int newStudentCnt(String userId) {
        int newStudentCnt = dao.newStudentCnt(userId);
        return newStudentCnt;
    }

    @Override
    public double ratingAvg(String userId) {
        double ratingAvg = dao.ratingAvg(userId);
        return ratingAvg;
    }

    @Override
    public List<RevenueGraphDTO> getRevenueGraph(String userId) {

        List<RevenueGraphDTO> revenueGraph = dao.getRevenueGraph(userId);
        return revenueGraph;
    }

    @Override
    public List<AgeGraphDTO> getAgeGraph(String userId) {

        List<AgeGraphDTO> ageGraph = dao.getAgeGraph(userId);
        return ageGraph;
    }

    //REST@@@@@@@@@@@@@


}
