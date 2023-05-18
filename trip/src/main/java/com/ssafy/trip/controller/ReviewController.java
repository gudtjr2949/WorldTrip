package com.ssafy.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.dto.review.ImageDto;
import com.ssafy.trip.dto.review.ReviewDto;
import com.ssafy.trip.service.review.ReviewCommentService;
import com.ssafy.trip.service.review.ReviewImageService;
import com.ssafy.trip.service.review.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;
    private ReviewCommentService reviewCommentService;
    private ReviewImageService reviewImageService;

    ObjectMapper objectMapper = new ObjectMapper();

    public ReviewController(ReviewService reviewService, ReviewCommentService reviewCommentService, ReviewImageService reviewImageService) {
        this.reviewService = reviewService;
        this.reviewCommentService = reviewCommentService;
        this.reviewImageService = reviewImageService;
    }

    @PostMapping("/api/write")
    public ResponseEntity<Map<String, String>> write(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

//        List<ImageDto> images = Arrays.asList(objectMapper.convertValue(map.get("image"), ImageDto[].class));
        try {
            ReviewDto reviewDto = new ReviewDto();

            reviewDto.setUser_id(map.get("user_id"));
            reviewDto.setTitle(map.get("title"));
            reviewDto.setContent(map.get("content"));

            reviewService.write(reviewDto);
//            reviewImageService.insert(images);

            res.put("resmsg", "후기 게시글 작성 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 작성 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/list")
    public ResponseEntity<Map<String, Object>> list(@RequestBody(required = false) Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("list", reviewService.list(map));
            res.put("resmsg", "후기 게시글 전체 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 전체 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/detail/{review_id}")
    public ResponseEntity<Map<String, Object>> select(@PathVariable("review_id") int review_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            // 댓글 가져오기
            res.put("comment", reviewCommentService.list(review_id));
            res.put("review", reviewService.select(review_id));
            res.put("image", reviewImageService.list(review_id));
            reviewService.updateHit(review_id);
            res.put("resmsg", "후기 게시글 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    // 내 글만 조회
    @GetMapping("/api/my_review/{user_id}")
    public ResponseEntity<Map<String, Object>> my_review(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("review", reviewService.my_list(user_id));

            res.put("resmsg", "내 후기 게시글 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "내 후기 게시글 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    // 팔로워 글 조회
    @GetMapping("/api/follower_review/{user_id}")
    public ResponseEntity<Map<String, Object>> follower_review(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            List<ReviewDto> list = reviewService.follower_list(user_id);

            res.put("review", list);
            res.put("resmsg", "팔로워 후기 게시글 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "팔로워 후기 게시글 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/modify/{review_id}")
    public ResponseEntity<Map<String, String>> modify(@RequestBody Map<String, String> map, @PathVariable("review_id") int review_id) {
        Map<String, String> res = new HashMap<>();

//        List<ImageDto> images = Arrays.asList(objectMapper.convertValue(map.get("image"), ImageDto[].class));

        try {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReview_id(review_id);
            reviewDto.setTitle(map.get("title"));
            reviewDto.setContent(map.get("content"));

            if (map.get("plan_id") != null) {
                reviewDto.setPlan_id(Integer.parseInt(map.get("plan_id")));
            }

            reviewService.modify(reviewDto);
//            reviewImageService.modify(images);

            res.put("resmsg", "후기 게시글 수정 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 수정 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/like/{review_id}")
    public ResponseEntity<Map<String, String>> like(@RequestBody Map<String, String> map, @PathVariable("review_id") int review_id) {
        Map<String, String> res = new HashMap<>();
        try {
            reviewService.updateHit(review_id);
            res.put("resmsg", "후기 게시글 좋아요 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 좋아요 실패");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/api/delete/{review_id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("review_id") int review_id) {
        Map<String, String> res = new HashMap<>();

        try {
            reviewService.delete(review_id);
            res.put("resmsg", "후기 게시글 삭제 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 삭제 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }
}
