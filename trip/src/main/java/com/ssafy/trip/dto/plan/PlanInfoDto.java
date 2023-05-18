package com.ssafy.trip.dto.plan;

import com.ssafy.trip.dto.place.PlaceDto;
import lombok.Data;

import java.util.List;

@Data
public class PlanInfoDto {

    private PlanDto planDto;
    private List<PlaceDto> placeDtoList;

}
