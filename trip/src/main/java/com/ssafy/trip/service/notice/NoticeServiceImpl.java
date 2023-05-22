package com.ssafy.trip.service.notice;

import com.ssafy.trip.dto.notice.NoticeDto;
import com.ssafy.trip.repository.notice.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    private NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public void write(NoticeDto noticeDto) {
        noticeRepository.write(noticeDto);
    }

    @Override
    public int getMaxReviewId() {
        return 0;
    }

    @Override
    public List<NoticeDto> list(Map<String, String> map) {
        return noticeRepository.list(map);
    }

    @Override
    public NoticeDto select(int notice_id) {
        return noticeRepository.select(notice_id);
    }

    @Override
    public void modify(NoticeDto noticeDto) {
        noticeRepository.modify(noticeDto);
    }

    @Override
    public void delete(int notice_id) {
        noticeRepository.delete(notice_id);
    }

    @Override
    public void updateHit(int notice_id) {
        noticeRepository.updateHit(notice_id);
    }
}
