package com.ssafy.trip.dto.plan;

import lombok.Data;

@Data
public class PlanCommentDto {
    private int comment_id;
    private int plan_id;
    private String user_id;
    private String content;
    private String register_time;
    private int like;
}
