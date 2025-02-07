package com.example.greenlast.service.kwanhyun;

import com.example.greenlast.dao.kwanhyun.AlarmDao;
import com.example.greenlast.dto.AlarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Created on 2025-01-23 by 노관현
 */

@Service
public class AlarmService {

    private final AlarmDao alarmDao;

    @Autowired
    public AlarmService(AlarmDao alarmDao) {
        this.alarmDao = alarmDao;
    }

    /*
        알림 생성하는 메소드
     */
    public void createAlarm(String userId, String alarmContent, String alarmUrl) {
        AlarmDTO alarm = new AlarmDTO();
        alarm.setUserId(userId);
        alarm.setAlarmContent(alarmContent);
        alarm.setAlarmUrl(alarmUrl);

        alarmDao.createAlarm(alarm);
    }

    /*
        해당 유저의 아이디에 해당하는 미확인 알림 불러오는 메소드
     */
    public void getUnreadAlarm(int userId) {
        List<AlarmDTO> unreadAlarmList = alarmDao.getUnreadAlarm(userId);
    }

    /*
        해당 유저의 아이디에 해당하는 확인한 알림 불러오는 메소드
     */
    public void getReadAlarm(int userId) {
        List<AlarmDTO> ReadAlarmList = alarmDao.getReadAlarm(userId);
    }
    /*
        알림 읽었을 때 읽음처리하는 메소드
     */
    public void readAlarm(int alarmId) {
        alarmDao.readAlarm(alarmId);
    }

}
