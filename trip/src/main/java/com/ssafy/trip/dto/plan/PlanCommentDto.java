package com.ssafy.trip.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlanCommentDto {
    int plan_id;
    String user_id;
    String content;
    String register_time;
    int like;
}
