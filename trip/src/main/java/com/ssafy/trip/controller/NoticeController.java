package com.ssafy.trip.controller;

import com.ssafy.trip.dto.notice.NoticeDto;
import com.ssafy.trip.dto.review.ImageDto;
import com.ssafy.trip.dto.review.ReviewCommentDto;
import com.ssafy.trip.dto.review.ReviewDto;
import com.ssafy.trip.service.notice.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/api/write")
    public ResponseEntity<Map<String, String>> write(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        try {
            NoticeDto noticeDto = new NoticeDto();

            noticeDto.setUser_id(map.get("user_id"));
            noticeDto.setTitle(map.get("title"));
            noticeDto.setContent(map.get("content"));

            noticeService.write(noticeDto);

            res.put("resmsg", "공지 게시글 작성 성공");
        } catch (Exception e) {
            res.put("resmsg", "공지 게시글 작성 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/list")
    public ResponseEntity<Map<String, Object>> list() {
        Map<String, Object> res = new HashMap<>();

        Map<String, String> map = new HashMap<>();

        try {
            res.put("list", noticeService.list(map));

            res.put("resmsg", "공지 게시글 전체 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "공지 게시글 전체 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/detail/{notice_id}")
    public ResponseEntity<Map<String, Object>> select(@PathVariable("notice_id") int notice_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            NoticeDto noticeDto = noticeService.select(notice_id);
            res.put("notice", noticeDto);

            noticeService.updateHit(notice_id);
            res.put("resmsg", "공지 게시글 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "공지 게시글 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/modify")
    public ResponseEntity<Map<String, String>> modify(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();


        try {
            NoticeDto noticeDto = new NoticeDto();
            noticeDto.setTitle(map.get("title"));
            noticeDto.setContent(map.get("content"));

            noticeService.modify(noticeDto);

            res.put("resmsg", "공지 게시글 수정 성공");
        } catch (Exception e) {
            res.put("resmsg", "공지 게시글 수정 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/api/delete/{notice_id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("notice_id") int notice_id) {
        Map<String, String> res = new HashMap<>();

        try {
            noticeService.delete(notice_id);
            res.put("resmsg", "공지 게시글 삭제 성공");
        } catch (Exception e) {
            res.put("resmsg", "공지 게시글 삭제 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }
}
