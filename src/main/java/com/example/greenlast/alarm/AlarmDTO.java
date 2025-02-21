package com.example.greenlast.alarm;

/**
 * Created on 2025-01-22 by 한상인
 */
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlarmDTO {

    private int alarmId;
    private String userId; // 알림 수신자 id
    private String alarmContent;
    private String relatedUrl;
    private int relatedId; // 게시글 id 등 참조할 id
    private String alarmType;
    private int readStatus;
    private String alarmDate;

}