package com.ssafy.trip.repository.review;

import com.ssafy.trip.dto.review.ImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewImageRepository {
    void insert(List<ImageDto> images);
    List<ImageDto> list(int review_id);
    void modify(List<ImageDto> images);
}
