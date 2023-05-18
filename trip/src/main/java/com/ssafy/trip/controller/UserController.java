package com.ssafy.trip.controller;

import com.ssafy.trip.dto.user.UserDto;
import com.ssafy.trip.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/idCheck/{user_id}")
    public ResponseEntity<Map<String, Object>> idCheck(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("check", userService.idCheck(user_id));
            res.put("resmsg", "아이디 체크 성공");
        } catch (Exception e) {
            res.put("resmsg", "아이디 체크 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/join")
    public ResponseEntity<Map<String, String>> join(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        try {
            UserDto userDto = new UserDto();

            userDto.setUser_id(map.get("user_id"));
            userDto.setUser_pw(map.get("user_pw"));
            userDto.setUser_name(map.get("user_name"));
            userDto.setUser_email(map.get("user_email"));
            userDto.setUser_domain(map.get("user_domain"));

            userService.join(userDto);

            res.put("resmsg", "회원가입 성공");
        } catch (Exception e) {
            res.put("resmsg", "회원가입 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> map, HttpSession session) {
        Map<String, String> res = new HashMap<>();

        String user_id = map.get("user_id");
        String user_pw = map.get("user_pw");

        try {
            UserDto userDto = userService.login(user_id, user_pw);

            if (userDto != null) {
                session.setAttribute("user_id", userDto.getUser_id());
                session.setAttribute("user_name", userDto.getUser_name());

                res.put("resmsg", "로그인 성공");
            } else {
                res.put("resmsg", "로그인 실패");
            }
        } catch (Exception e) {
            res.put("resmsg", "로그인 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        Map<String, String> res = new HashMap<>();

        try {
            // 세션 무효화
            session.invalidate();

            res.put("resmsg", "로그아웃 성공");
        } catch (Exception e) {
            res.put("resmsg", "로그아웃 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }


    @GetMapping("/api/follower_list/{user_id}")
    public ResponseEntity<Map<String, Object>> follower_list(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            List<UserDto> list = userService.follower_list(user_id);

            res.put("list", list);
            res.put("resmsg", "날 팔로우하는 사람 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "날 팔로우하는 사람 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/following_list/{user_id}")
    public ResponseEntity<Map<String, Object>> following_list(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            List<UserDto> list = userService.following_list(user_id);

            res.put("list", list);
            res.put("resmsg", "내가 팔로우하는 사람 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "내가 팔로우하는 사람 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/modify")
    public ResponseEntity<Map<String, String>> modify(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        try {
            UserDto userDto = new UserDto();

            userDto.setUser_id(map.get("user_id"));
            userDto.setUser_pw(map.get("user_pw"));
            userDto.setUser_name(map.get("user_name"));
            userDto.setUser_email(map.get("user_email"));
            userDto.setUser_domain(map.get("user_domain"));

            userService.modify(userDto);

            res.put("resmsg", "회원정보 수정 성공");
        } catch (Exception e) {
            res.put("resmsg", "회원정보 수정 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/api/delete/{user_id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("user_id") String user_id, HttpSession session) {
        Map<String, String> res = new HashMap<>();

        try {
            userService.delete(user_id);

            session.invalidate();
            res.put("resmsg", "회원정보 삭제 성공");
        } catch (Exception e) {
            res.put("resmsg", "회원정보 삭제 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/checkAdmin/{user_id}")
    public ResponseEntity<Map<String, Object>> checkAdmin(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("check", userService.checkAdmin(user_id));
            res.put("resmsg", "Admin 여부 확인 성공");
        } catch (Exception e) {
            res.put("resmsg", "Admin 여부 확인 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }
}