package com.example.greenlast.dao.joontaek;

import com.example.greenlast.dto.ScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleDao {

    void insertSchedule(ScheduleDTO schedule);

}
