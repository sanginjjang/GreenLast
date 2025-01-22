package com.example.greenlast.controllers.api.joontaek;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @PostMapping("/save")
    public ResponseEntity<?> saveEvent(@RequestBody Map<String, Object> eventData) {
        System.out.println("일정 추가됨: " + eventData);
        return ResponseEntity.ok(Map.of("success", true, "id", 1));  // 일정 ID 반환
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        System.out.println("일정 삭제됨: " + id);
        return ResponseEntity.ok(Map.of("success", true));
    }

}
