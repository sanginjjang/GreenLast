package com.example.greenlast.controllers.api.joontaek;

import com.example.greenlast.dto.AgeGraphDTO;
import com.example.greenlast.dto.RevenueGraphDTO;
import com.example.greenlast.dto.ScheduleDTO;
import com.example.greenlast.service.joontaek.ClassDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {


    @Autowired
    ClassDashboardService dashboardService;

    @RequestMapping("revenueGraph")
    public ResponseEntity<List<RevenueGraphDTO>> revenueGraph() {
        // 나중에 시큐리티 값 넣을 예정
        List<RevenueGraphDTO> revenueGraph = dashboardService.getRevenueGraph("이동하");

        System.err.println(revenueGraph);

        return ResponseEntity.ok(revenueGraph);
    }

    @RequestMapping("ageGraph")
    public ResponseEntity<List<AgeGraphDTO>> ageGraph() {
        // 나중에 시큐리티 값 넣을 예정
        List<AgeGraphDTO> ageGraph = dashboardService.getAgeGraph("이동하");

        System.err.println("에이지그래프...ㅅㅂ"+ageGraph);

        return ResponseEntity.ok(ageGraph);
    }



}

