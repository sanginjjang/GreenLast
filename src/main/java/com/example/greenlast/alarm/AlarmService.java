package com.example.greenlast.alarm;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Created on 2025-02-19 by 노관현
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private static final long TIMEOUT = 60 * 1000L;

    private final Map<String, List<SseEmitter>> AlarmEmitters = new ConcurrentHashMap<>();

    private final AlarmDao alarmDao;

    public SseEmitter subscribe(String userId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        AlarmEmitters.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> AlarmEmitters.remove(userId));
        emitter.onTimeout(() -> AlarmEmitters.remove(userId));
        emitter.onError((e) -> AlarmEmitters.remove(userId));

        try {
            emitter.send(SseEmitter.event().name("ping").data("connected"));
        } catch (IOException e) {
            log.info(e.toString());
        }

        return emitter;
    }

    // 관리자가 전체 알림 발송해야 할 시 사용
    public void sendToAll(String message) {
        AlarmEmitters.forEach((userId, emitters) -> {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().name("broadcast").data(message));
                } catch (IOException e) {
                    removeEmitter(userId, emitter);
                    log.info("전체 알림 전송 실패: {}", e.getMessage());
                }
            }
        });
    }

    private void removeEmitter(String userId, SseEmitter emitter) {
        List<SseEmitter> emitters = AlarmEmitters.get(userId);
        if (emitters != null) {
            emitters.remove(emitter);
            if (emitters.isEmpty()) {
                AlarmEmitters.remove(userId);
            }
        }
    }

    public void createAlarm(AlarmDTO alarmDto) {
        if (alarmDto.getRelatedUrl() == null || alarmDto.getRelatedUrl().isEmpty()) {
            switch (alarmDto.getAlarmType()) {
                case "COMMENT":
                    alarmDto.setRelatedUrl("/kwanhyun/community/CommunityDetail?postId=");
                    break;
                case "NOTICE":
                    alarmDto.setRelatedUrl("/kwanhyun/community/CommunityMain?pageType=free");
                    break;
                // 알람타입에 따라서 보낼 url 주소 매핑하는곳 여기에 추가하면 됩니당~

                default:
                    alarmDto.setRelatedUrl("");
            }
        }
        alarmDao.createAlarm(alarmDto);
    }

    public List<AlarmDTO> getAlarms(String userId) {
        return alarmDao.getAlarms(userId);
    }

    public int getAlarmCount(String userId) {
        return alarmDao.getAlarmCount(userId);
    }

    public void deleteAlarm(int alarmId, String userId) {
        alarmDao.deleteAlarm(alarmId, userId);
    }

    public void markAsRead(int alarmId, String userId) {
        alarmDao.markAsRead(alarmId, userId);
    }
}