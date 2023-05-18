package com.ssafy.trip.service.review;

import com.ssafy.trip.dto.review.ReviewCommentDto;
import com.ssafy.trip.repository.review.ReviewCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewCommentServiceImpl implements ReviewCommentService {

    ReviewCommentRepository reviewCommentRepository;

    public ReviewCommentServiceImpl(ReviewCommentRepository reviewCommentRepository) {
        this.reviewCommentRepository = reviewCommentRepository;
    }

    @Override
    public void write(ReviewCommentDto reviewCommentDto) {
        reviewCommentRepository.write(reviewCommentDto);
    }

    @Override
    public List<ReviewCommentDto> list(int review_id) {
        return reviewCommentRepository.list(review_id);
    }

    @Override
    public void modify(ReviewCommentDto reviewCommentDto) {
        reviewCommentRepository.modify(reviewCommentDto);
    }

    @Override
    public void updateLike(ReviewCommentDto reviewCommentDto) {
        reviewCommentRepository.updateLike(reviewCommentDto);
    }

    @Override
    public void delete(ReviewCommentDto reviewCommentDto) {
        reviewCommentRepository.delete(reviewCommentDto);
    }
}
