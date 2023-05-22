package com.ssafy.trip.service.review;

import com.ssafy.trip.dto.review.ReviewDto;
import com.ssafy.trip.repository.review.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void write(ReviewDto reviewDto) {
        reviewRepository.write(reviewDto);
    }

    @Override
    public int getMaxReviewId() {
        return reviewRepository.getMaxReviewId();
    }

    @Override
    public List<ReviewDto> list(Map<String, String> map) {
        return reviewRepository.list(map);
    }

    @Override
    public ReviewDto select(int review_id) {
        return reviewRepository.select(review_id);
    }

    @Override
    public List<ReviewDto> my_list(String user_id) {
        return reviewRepository.my_list(user_id);
    }

    @Override
    public List<ReviewDto> follower_list(String user_id) {
        return reviewRepository.follower_list(user_id);
    }

    @Override
    public void modify(ReviewDto reviewDto) {
        reviewRepository.modify(reviewDto);
    }

    @Override
    public void delete(int review_id) {
        reviewRepository.delete(review_id);
    }

    @Override
    public void updateLike(int review_id) {
        reviewRepository.updateLike(review_id);
    }

    @Override
    public void updateHit(int review_id) {
        reviewRepository.updateHit(review_id);
    }
}
