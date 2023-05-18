package com.ssafy.trip.service.review;

import com.ssafy.trip.dto.review.ImageDto;
import com.ssafy.trip.repository.review.ReviewImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewImageServiceImpl implements ReviewImageService {

    ReviewImageRepository reviewImageRepository;

    public ReviewImageServiceImpl(ReviewImageRepository reviewImageRepository) {
        this.reviewImageRepository = reviewImageRepository;
    }

    @Override
    public void insert(List<ImageDto> images) {
        reviewImageRepository.insert(images);
    }

    @Override
    public List<ImageDto> list(int review_id) {
        return reviewImageRepository.list(review_id);
    }

    @Override
    public void modify(List<ImageDto> images) {
        reviewImageRepository.modify(images);
    }
}
