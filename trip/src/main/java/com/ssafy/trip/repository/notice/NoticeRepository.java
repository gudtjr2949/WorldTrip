package com.ssafy.trip.repository.notice;

import com.ssafy.trip.dto.notice.NoticeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeRepository {
    void write(NoticeDto noticeDto);
    int getMaxReviewId();
    List<NoticeDto> list(Map<String, String> map);
    NoticeDto select(int notice_id);
    void modify(NoticeDto noticeDto);
    void delete(int notice_id);
    void updateHit(int notice_id);
}
