package com.ssafy.trip.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
public class UserDto {
    String user_id;
    String user_pw;
    String user_name;
    String user_email;
    String user_domain;
    String register_time;
    String role; // default : user
}
