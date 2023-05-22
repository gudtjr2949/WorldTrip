package com.ssafy.trip.service.user;

import com.ssafy.trip.dto.user.UserDto;
import com.ssafy.trip.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean idCheck(String user_id) {
        if (userRepository.idCheck(user_id) == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void join(UserDto userDto) {
        userRepository.join(userDto);
    }

    @Override
    public UserDto login(String user_id, String user_pw) {
        return userRepository.login(user_id, user_pw);
    }

    @Override
    public UserDto mypage(String user_id) {
        return userRepository.mypage(user_id);
    }

    @Override
    public List<UserDto> follower_list(String user_id) {
        return userRepository.follower_list(user_id);
    }

    @Override
    public List<UserDto> following_list(String user_id) {
        return userRepository.following_list(user_id);
    }

    @Override
    public void modify(UserDto userDto) {
        userRepository.modify(userDto);
    }

    @Override
    public void delete(String user_id) {
        userRepository.delete(user_id);
    }

    @Override
    public boolean checkAdmin(String user_id) {
        if (userRepository.checkAdmin(user_id).equals("admin")) {
            return true;
        } else {
            return false;
        }
    }
}
