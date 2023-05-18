package com.ssafy.trip.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
public class FollowingDto {
    String user_id;
    String following_id;
}
