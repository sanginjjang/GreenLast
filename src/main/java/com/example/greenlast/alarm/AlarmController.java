package com.example.greenlast.alarm;

/*
 * Created on 2025-02-19 by 노관현
 */


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;


    @GetMapping("/subscribe/{userId}")
    public SseEmitter subscribe(@PathVariable("userId") String userId) {
        return alarmService.subscribe(userId);
    }

    // Todo : Sse 사용해서 알람 실시간으로 보내는 메소드


    public String setContentByAlarmType(AlarmDTO alarmDTO) {
        switch (alarmDTO.getAlarmType()) {
            case "COMMENT" :
                return "등록한 게시글에 댓글이 달렸습니다.";
            case "NOTICE":
                return "새로운 공지가 등록되었습니다.";
            default:
                return "새로운 알림이 도착했습니다.";
        }
    }

    @PostMapping("/alarm")
    public String createAlarm(@RequestBody AlarmDTO alarmdto) {
        alarmdto.setAlarmContent(setContentByAlarmType(alarmdto));

        alarmService.createAlarm(alarmdto);
        return "알림 발송된 아이디 : " + alarmdto.getUserId() + "알림 내용 :" + alarmdto.getAlarmContent();
    }

    @GetMapping("/alarms")
    public List<AlarmDTO> getAlarms(@RequestParam("userId") String userId) {
        return alarmService.getAlarms(userId);
    }

    @GetMapping("/alarms/countAll")
    public int getAlarmCount(@RequestParam("userId") String userId) {
        return alarmService.getAlarmCount(userId);
    }

    @DeleteMapping("/alarms/{alarmId}")
    public String deleteAlarm(@PathVariable("alarmId") int alarmId, @RequestParam("userId") String userId) {
        System.out.println(alarmId);
        System.out.println(alarmId);
        System.out.println(userId);
        System.out.println(userId);
        alarmService.deleteAlarm(alarmId, userId);
        return "알림 삭제 완료";
    }

    @PatchMapping("/read/{alarmId}")
    public String markAsRead(@PathVariable("alarmId") int alarmId, @RequestBody Map<String, String> requestData) {
        String userId = requestData.get("userId");
        alarmService.markAsRead(alarmId, userId);
        return "알림 읽음 처리 완료";
    }

}
