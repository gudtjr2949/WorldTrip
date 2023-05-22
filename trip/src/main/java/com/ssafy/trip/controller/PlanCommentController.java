package com.ssafy.trip.controller;

import com.ssafy.trip.dto.plan.PlanCommentDto;
import com.ssafy.trip.dto.plan.PlanDto;
import com.ssafy.trip.dto.review.ReviewCommentDto;
import com.ssafy.trip.service.plan.PlanCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plancomment")
public class PlanCommentController {

    private PlanCommentService planCommentService;

    public PlanCommentController(PlanCommentService planCommentService) {
        this.planCommentService = planCommentService;
    }


    @GetMapping("/api/{plan_id}")
    ResponseEntity<Map<String, Object>> list(@PathVariable("plan_id") String id){
        Map<String, Object> map = new HashMap<>();
        int plan_id = Integer.parseInt(id);

        try {
            List<ReviewCommentDto> list = planCommentService.list(plan_id);
            map.put("list", list);

            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api")
    ResponseEntity<Map<String, Object>> write(@RequestBody Map<String, Object> map){

        PlanCommentDto planCommentDto = new PlanCommentDto();
        planCommentDto.setPlan_id(Integer.parseInt((String)map.get("plan_id")));
        planCommentDto.setUser_id((String) map.get("user_id"));
        planCommentDto.setContent((String) map.get("content"));
        System.out.println(planCommentDto);
        try {
            planCommentService.write(planCommentDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/api")
    ResponseEntity<Map<String, Object>> modify(@RequestBody Map<String, Object> map){

        PlanCommentDto planCommentDto = new PlanCommentDto();
        planCommentDto.setComment_id(Integer.parseInt((String)map.get("comment_id")));
        planCommentDto.setPlan_id(Integer.parseInt((String)map.get("plan_id")));
        planCommentDto.setUser_id((String) map.get("user_id"));
        planCommentDto.setContent((String) map.get("content"));

        try {
            planCommentService.modify(planCommentDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/{comment_id}")
    ResponseEntity<Map<String, Object>> delete(@PathVariable("comment_id") String id){

        int comment_id = Integer.parseInt(id);

        try {
            planCommentService.deleteComment(comment_id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/uplike")
    ResponseEntity<Map<String, Object>> upLike(@RequestBody Map<String, Object> map){

        PlanCommentDto planCommentDto = new PlanCommentDto();
        int comment_id = Integer.parseInt((String) map.get("comment_id"));
        String user_id = (String) map.get("user_id");

        planCommentDto.setComment_id(comment_id);
        planCommentDto.setUser_id(user_id);

        try {
            planCommentService.upLike(comment_id);
            planCommentService.make(planCommentDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/downlike")
    ResponseEntity<Map<String, Object>> downLike(@RequestBody Map<String, Object> map){

        PlanCommentDto planCommentDto = new PlanCommentDto();
        int comment_id = Integer.parseInt((String) map.get("comment_id"));
        String user_id = (String) map.get("user_id");

        planCommentDto.setComment_id(comment_id);
        planCommentDto.setUser_id(user_id);
        try {
            planCommentService.downLike(comment_id);
            planCommentService.delete(planCommentDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
