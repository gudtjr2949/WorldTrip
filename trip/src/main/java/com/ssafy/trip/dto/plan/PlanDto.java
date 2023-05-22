package com.ssafy.trip.dto.plan;

import lombok.Data;

@Data
public class PlanDto {

    private int plan_id;
    private String user_id;
    private String title;
    private String content;
    private String register_time;
    private int like;
    private int hit;
    private String start_date;
    private String end_date;
    private String store;

}
