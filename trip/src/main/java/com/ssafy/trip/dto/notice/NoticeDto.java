package com.ssafy.trip.dto.notice;

import lombok.Data;

@Data
public class NoticeDto {
    int notice_id;
    String user_id;
    String title;
    String content;
    String register_time;
}
