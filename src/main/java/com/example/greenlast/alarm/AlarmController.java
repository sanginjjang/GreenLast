package com.example.greenlast.alarm;

/*
 * Created on 2025-02-19 by 노관현
 */


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;


    @GetMapping("/subscribe/{userId}")
    public SseEmitter subscribe(@PathVariable("userId") String userId) {
        return alarmService.subscribe(userId);
    }

    @GetMapping("/sendAlarm/{userId}/{alarmType}")
    public String sendAlarm(@PathVariable("userId") String userId, @PathVariable("alarmType") String alarmType) {
        String message = setMessageByAlarmType(alarmType);
        alarmService.sendAlarm(userId, message);
        return "알림 발송된 아이디 : " + userId + "알림 내용 :" + message;
    }

    public String setMessageByAlarmType(String alarmType) {
        switch (alarmType) {
            case "COMMENT" :
                return "등록한 게시글에 댓글이 달렸습니다.";
            case "NOTICE":
                return "새로운 공지가 등록되었습니다.";
            default:
                return "새로운 알림이 도착했습니다.";
        }
    }

    @PostMapping("/alarm")
    public void createAlarm(@RequestBody AlarmDTO alarmdto) {

    }

    @GetMapping("/alarms")
    public List<AlarmDTO> getAlarms(@RequestParam("userId") String userId) {
        return alarmService.getAlarms(userId);
    }

    @GetMapping("/alarms/countAll")
    public int getAlarmCount(@RequestParam("userId") String userId) {
        return alarmService.getAlarmCount(userId);
    }

    @GetMapping("/alarms/unread")
    public List<AlarmDTO> getUnreadAlarms(@RequestParam("userId") String userId) {
        return alarmService.getUnreadAlarms(userId);
    }

    @GetMapping("/alarms/unread/count")
    public int getUnreadAlarmCount(@RequestParam("userId") String userId) {
        return alarmService.getUnreadAlarmCount(userId);
    }

    @GetMapping("/alarms/read")
    public List<AlarmDTO> getReadAlarms(@RequestParam("userId") String userId) {
        return alarmService.getReadAlarms(userId);
    }

    @GetMapping("/alarms/read/count")
    public int getReadAlarmCount(@RequestParam("userId") String userId) {
        return alarmService.getReadAlarmCount(userId);
    }

    @DeleteMapping("/alarms/{alarmId}")
    public String deleteAlarm(@PathVariable("alarmId") int alarmId, @RequestParam("userId") String userId) {
        alarmService.deleteAlarm(alarmId, userId);
        return "알림 삭제 완료";
    }

    @PatchMapping("/alarms/read/{alarmId}")
    public String markAsRead(@PathVariable("alarmId") int alarmId, @RequestParam("userId") String userId) {
        alarmService.markAsRead(alarmId, userId);
        return "알림 읽음 처리 완료";
    }

}
