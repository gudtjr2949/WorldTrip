package com.ssafy.trip.repository.review;

import com.ssafy.trip.dto.review.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewRepository {
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
