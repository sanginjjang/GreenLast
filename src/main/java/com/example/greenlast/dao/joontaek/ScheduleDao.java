package com.example.greenlast.dao.joontaek;

import com.example.greenlast.dto.ScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleDao {

    void insertSchedule(ScheduleDTO schedule);

    List<ScheduleDTO> getSchedule(String userId);

    int deleteSchedule(Long scheduleId);

    List<ScheduleDTO> getScheduleByDate(String date);

    boolean updateSchedule(ScheduleDTO schedule);
}
