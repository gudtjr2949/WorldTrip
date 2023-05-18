package com.ssafy.trip.repository.review;

import com.ssafy.trip.dto.review.ReviewCommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewCommentRepository {
    void write(ReviewCommentDto reviewCommentDto);
    List<ReviewCommentDto> list(int review_id);
    void modify(ReviewCommentDto reviewCommentDto);
    void updateLike(ReviewCommentDto reviewCommentDto);
    void delete(ReviewCommentDto reviewCommentDto);
}
