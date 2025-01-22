package com.example.greenlast.controllers.api.joontaek;

import com.example.greenlast.dao.joontaek.ScheduleDao;
import com.example.greenlast.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleDao scheduleDao;

    @PostMapping("/save")
    public ResponseEntity<?> saveEvent(@RequestBody ScheduleDTO schedule) {
        System.out.println("@@@@@@@@@@@@");
        System.out.println(schedule);
        System.out.println("@@@@@@@@@@@@");

        //임시로 유저아이디 저장^^
        schedule.setUserId("박준택");

        scheduleDao.insertSchedule(schedule);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
