package com.ssafy.trip.service.review;

import com.ssafy.trip.dto.review.ReviewCommentDto;

import java.util.List;

public interface ReviewCommentService {
    void write(ReviewCommentDto reviewCommentDto);
    List<ReviewCommentDto> list(int review_id);
    void modify(ReviewCommentDto reviewCommentDto);
    void updateLike(ReviewCommentDto reviewCommentDto);
    void delete(ReviewCommentDto reviewCommentDto);
}
