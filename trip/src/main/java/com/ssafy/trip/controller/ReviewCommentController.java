package com.ssafy.trip.controller;

import com.ssafy.trip.dto.review.ReviewCommentDto;
import com.ssafy.trip.service.review.ReviewCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/review_comment")
public class ReviewCommentController {

    private ReviewCommentService reviewCommentService;

    public ReviewCommentController(ReviewCommentService reviewCommentService) {
        this.reviewCommentService = reviewCommentService;
    }

    @PostMapping("/api/write")
    public ResponseEntity<Map<String, String>> write(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        try {
            ReviewCommentDto reviewCommentDto = new ReviewCommentDto();

            reviewCommentDto.setReview_id(Integer.parseInt(map.get("review_id")));
            reviewCommentDto.setUser_id(map.get("user_id"));
            reviewCommentDto.setContent(map.get("content"));

            reviewCommentService.write(reviewCommentDto);

            res.put("resmsg", "후기 게시글 댓글 작성 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 댓글 작성 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/modify/{review_id}")
    public ResponseEntity<Map<String, String>> modify(@RequestBody Map<String, String> map, @PathVariable int review_id) {
        Map<String, String> res = new HashMap<>();

        try {
            ReviewCommentDto reviewCommentDto = new ReviewCommentDto();

            reviewCommentDto.setReview_id(review_id);
            reviewCommentDto.setComment_id(Integer.parseInt(map.get("comment_id")));
            reviewCommentDto.setContent(map.get("content"));

            reviewCommentService.modify(reviewCommentDto);
            res.put("resmsg", "후기 게시글 댓글 수정 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 댓글 수정 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/like/{review_id}")
    public ResponseEntity<Map<String, String>> updateLike(@RequestBody Map<String, String> map, @PathVariable int review_id) {
        Map<String, String> res = new HashMap<>();

        try {
            ReviewCommentDto reviewCommentDto = new ReviewCommentDto();

            reviewCommentDto.setReview_id(review_id);
            reviewCommentDto.setComment_id(Integer.parseInt(map.get("comment_id")));

            reviewCommentService.updateLike(reviewCommentDto);

            res.put("resmsg", "후기 게시글 댓글 좋아요 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 댓글 좋아요 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/api/delete")
    public ResponseEntity<Map<String, String>> delete(@RequestBody Map<String, String> map)  {
        Map<String, String> res = new HashMap<>();

        try {
            ReviewCommentDto reviewCommentDto = new ReviewCommentDto();

            reviewCommentDto.setReview_id(Integer.parseInt(map.get("review_id")));
            reviewCommentDto.setComment_id(Integer.parseInt(map.get("comment_id")));

            reviewCommentService.delete(reviewCommentDto);
            res.put("resmsg", "후기 게시글 댓글 삭제 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 댓글 삭제 실패");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(res);
    }
}
