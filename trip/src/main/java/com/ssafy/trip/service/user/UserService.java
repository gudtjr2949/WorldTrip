package com.ssafy.trip.service.user;

import com.ssafy.trip.dto.user.UserDto;

import java.util.List;

public interface UserService {
    boolean idCheck(String user_id); // 아이디 중복 체크
    void join(UserDto userDto); // 회원가입
    UserDto login(String user_id, String user_pw); // 로그인
    UserDto mypage(String user_id);
    List<UserDto> follower_list(String user_id); // 날 팔로우하는 사람 조회
    List<UserDto> following_list(String user_id); // 내가 팔로우하는 사람 조회
    void modify(UserDto userDto); // 회원정보 수정
    void delete(String user_id); // 회원정보 삭제
    boolean checkAdmin(String user_id); // admin 여부 확인
}
