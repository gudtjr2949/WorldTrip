package com.ssafy.trip.service.notice;

import com.ssafy.trip.dto.notice.NoticeDto;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    void write(NoticeDto noticeDto);
    int getMaxReviewId();
    List<NoticeDto> list(Map<String, String> map);
    NoticeDto select(int notice_id);
    void modify(NoticeDto noticeDto);
    void delete(int notice_id);
    void updateHit(int notice_id);
}
