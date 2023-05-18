package com.ssafy.trip.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReviewCommentDto {
    int review_id;
    int comment_id;
    String user_id;
    String content;
    String register_time;
    int like;
}
