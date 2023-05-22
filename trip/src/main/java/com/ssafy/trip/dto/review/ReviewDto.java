package com.ssafy.trip.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
public class ReviewDto {
    int review_id;
    String user_id;
    String title;
    String content;
    String register_time;
    String first_image;
    int plan_id;
    int like;
    int hit;
}
