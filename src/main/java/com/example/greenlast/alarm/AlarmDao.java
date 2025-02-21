package com.example.greenlast.alarm;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlarmDao {
    void createAlarm(AlarmDTO alarmDto);
    List<AlarmDTO> getAlarms(@Param("userId") String userId);
    int getAlarmCount(@Param("userId") String userId);
    void deleteAlarm(@Param("alarmId") int alarmId, @Param("userId") String userId);
    void markAsRead(@Param("alarmId") int alarmId, @Param("userId") String userId);
}
