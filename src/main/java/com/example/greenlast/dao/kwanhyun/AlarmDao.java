package com.example.greenlast.dao.kwanhyun;

import com.example.greenlast.dto.AlarmDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created on 2025-01-23 by 노관현
 */

@Mapper
public interface AlarmDao {
    public void createAlarm(AlarmDTO alarm);
    public void readAlarm(int alarmId);
    public List<AlarmDTO> getUnreadAlarm(int userId);
    public List<AlarmDTO> getReadAlarm(int userId);
}