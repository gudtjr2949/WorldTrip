package com.ssafy.trip.service.review;

import com.ssafy.trip.dto.review.ReviewDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    void write(ReviewDto reviewDto);
    int getMaxReviewId();
    List<ReviewDto> list(Map<String, String> map);
    ReviewDto select(int review_id);
    List<ReviewDto> my_list(String user_id);
    List<ReviewDto> follower_list(String user_id);
    void modify(ReviewDto reviewDto);
    void delete(int review_id);
    void updateLike(int review_id);
    void updateHit(int review_id);
}
