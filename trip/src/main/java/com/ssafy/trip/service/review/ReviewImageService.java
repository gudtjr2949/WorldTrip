package com.ssafy.trip.service.review;

import com.ssafy.trip.dto.review.ImageDto;

import java.util.List;

public interface ReviewImageService {
    void insert(List<ImageDto> images);
    List<ImageDto> list(int review_id);
    List<ImageDto> firstImage();
    void modify(List<ImageDto> images);
}
