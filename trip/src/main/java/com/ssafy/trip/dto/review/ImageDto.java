package com.ssafy.trip.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ImageDto {
    int image_id;
    int review_id;
    String image_name;
    String image_path;
}
