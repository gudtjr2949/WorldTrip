package com.ssafy.trip.dto.place;

import lombok.Data;

import java.util.List;

@Data
public class PlaceDto {

    private String place_id;
    private String formatted_phone_number;
    private String url;
    private String international_phone_number;
    private String formatted_address;
    private String name;
    private String photoreference;
    private String overview;

}
