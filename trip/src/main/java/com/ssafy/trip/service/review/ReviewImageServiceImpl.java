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
        for (int i = 0 ; i < images.size() ; i++) {
            ImageDto imageDto = images.get(i);
            imageDto.setImage_id(i);
            reviewImageRepository.insert(imageDto);
        }
    }

    @Override
    public List<ImageDto> list(int review_id) {
        return reviewImageRepository.list(review_id);
    }

    @Override
    public List<ImageDto> firstImage() {
        return reviewImageRepository.firstImage();
    }

    @Override
    public void modify(List<ImageDto> images) {
        int review_id = images.get(0).getReview_id();

        reviewImageRepository.delete(review_id);

        for (int i = 0 ; i < images.size() ; i++) {
            reviewImageRepository.insert(images.get(i));
        }
    }
}
