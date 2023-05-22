package com.ssafy.trip.dto.plan;


import lombok.Data;

@Data
public class UserPlanDto {

    private int plan_id;
    private String name;
    private String vicinity;
    private String photo;
    private String url;
    private int sequence;

}
