package com.example.greenlast.controllers.api.joontaek;

import com.example.greenlast.dao.joontaek.ScheduleDao;
import com.example.greenlast.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/list")
    public ResponseEntity<List<ScheduleDTO>> getSchedules() {


        String userId = "박준택";
        List<ScheduleDTO> schedules = scheduleDao.getSchedule(userId);

        System.out.println("리스트조회@@@@@@@@@@@@@");
        System.err.println(schedules);

        return ResponseEntity.ok(schedules);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {

        int result = scheduleDao.deleteSchedule(id);

        if (result > 0) {
            return ResponseEntity.ok(Map.of("success", true, "message", "일정이 삭제되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "일정을 찾을 수 없습니다."));
        }
    }

    @GetMapping("/listByDate")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByDate(@RequestParam String date) {
        System.out.println("요청 받은 날짜: " + date);
        List<ScheduleDTO> schedules = scheduleDao.getScheduleByDate(date);

        if (schedules != null && !schedules.isEmpty()) {
            return ResponseEntity.ok(schedules);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        boolean result = scheduleDao.updateSchedule(scheduleDTO);
        return ResponseEntity.ok(Map.of("success", result));
    }
}
