package com.ssafy.trip.controller;

import com.google.gson.Gson;
import com.ssafy.trip.dto.place.PlaceDto;
import com.ssafy.trip.dto.place.PlanPlaceDto;
import com.ssafy.trip.dto.plan.PlanDto;
import com.ssafy.trip.dto.plan.PlanInfoDto;
import com.ssafy.trip.dto.plan.UserPlanDto;
import com.ssafy.trip.service.place.PlaceService;
import com.ssafy.trip.service.plan.PlanService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plan")
@CrossOrigin(origins = "http://localhost:8080")
public class PlanController {
    private PlanService planService;
    private PlaceService placeService;

    public PlanController(PlanService planService, PlaceService placeService) {
        this.planService = planService;
        this.placeService = placeService;
    }

    @GetMapping("/api/{user_id}") // user의 모든 계획 글 정보, 장소 정보
    ResponseEntity<Map<String, Object>> planListByUserId(@PathVariable String user_id){

        Map<String, Object> map = new HashMap<>();
        List<PlanInfoDto> list = new ArrayList<>();

        try {
            List<PlanDto> planDtoList = planService.listByUserId(user_id);

            for (PlanDto planDto : planDtoList) {
                PlanInfoDto planInfoDto = new PlanInfoDto();
                int plan_id = planDto.getPlan_id();
                List<PlaceDto> placeDtoList = new ArrayList<>();
                List<String> idList = placeService.placeIdByPlanId(plan_id);
                for (String id : idList) {
                    PlaceDto placeDto = placeService.infoByPlaceId(id);
                    placeDtoList.add(placeDto);
                }
                planInfoDto.setPlanDto(planDto);
                planInfoDto.setPlaceDtoList(placeDtoList);

                list.add(planInfoDto);
            }
            map.put("list", list);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api") // 계획 생성 : 글, 장소 정보 다 저장
    ResponseEntity<Map<String, Object>> make(@RequestBody Map<String, Object> map){
        System.out.println("post" + map);
        Map<String, Object> resmap = new HashMap<>();
        PlanDto planDto = new PlanDto();
//        planDto.setUser_id((String) map.get("user_id"));
        planDto.setUser_id("ssafy");
        planDto.setTitle((String) map.get("title"));
        planDto.setContent((String) map.get("content"));
        planDto.setStart_date((String) map.get("start_date"));
        planDto.setEnd_date((String) map.get("end_date"));
        planDto.setStore((String) map.get("store"));

        System.out.println(planDto);

        List<Map<String, Object>> plans = (List<Map<String, Object>>) map.get("plans");
        List<String> placeId = new ArrayList<>();


//        String user_id = (String) map.get("user_id");
        String user_id = "ssafy";

        try {
            planService.makePlan(planDto);
            int plan_id = planService.getPlanId(user_id);
            resmap.put("plan_id", plan_id);
            int idx = 1;

            for (Map<String, Object> plan : plans) {
                UserPlanDto userPlanDto = new UserPlanDto();
                userPlanDto.setPlan_id(plan_id);
                userPlanDto.setName((String) plan.get("name"));
                userPlanDto.setVicinity((String) plan.get("vicinity"));
                userPlanDto.setPhoto((String) plan.get("photo"));
                userPlanDto.setUrl((String) plan.get("url"));
                userPlanDto.setSequence(idx++);

                placeId.add((String) plan.get("place_id"));
                planService.userPlanMake(userPlanDto);
            }

            if (plans.size() < 1){
                UserPlanDto userPlanDto = new UserPlanDto();
                userPlanDto.setPlan_id(plan_id);
                planService.userPlanMake(userPlanDto);
            }


            idx = 1;
            for (String id : placeId) {
                PlanPlaceDto planPlaceDto = new PlanPlaceDto();
                if (!placeService.checkPlaceId(id)){
                    PlaceDto placeDto = detailInfo(id);
                    placeService.placeInfo(placeDto);
                }
                planPlaceDto.setPlan_id(plan_id);
                planPlaceDto.setPlace_id(id);
                planPlaceDto.setSequence(idx++);

                placeService.addPlanPlace(planPlaceDto);
            }
            return new ResponseEntity<>(resmap, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // place_id로 장소 세부 정보 가져옴
    PlaceDto detailInfo(String id) {
        String apiUrl = "https://maps.googleapis.com/maps/api/place/details/json?";
        String apiKey = "AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY";
//        String placeId = "ChIJnxzMERJnezURLofEAtU4RfQ";

        String url = apiUrl + "key=" + apiKey + "&place_id=" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), new ParameterizedTypeReference<Map<String, Object>>() {});

        if (responseEntity.getStatusCodeValue() == 200) {
            Map<String, Object> jsonResponse = responseEntity.getBody();
            Map<String, Object> result = (Map<String, Object>) jsonResponse.get("result");

            Map<String,Object> overview = (Map<String, Object>) ((Map<String, Object>) jsonResponse.get("result")).get("editorial_summary");
            try {
                result.put("overview", overview.get("overview"));
            }catch (Exception e){}

            List<Map<String, Object>> photo_reference = (List<Map<String, Object>>) ((Map<String, Object>) jsonResponse.get("result")).get("photos");
            try {
                result.put("photoreference", photo_reference.get(0).get("photo_reference"));
            }catch (Exception e){}


            // Gson을 이용하여 result를 PlaceDto 객체로 변환합니다.
            Gson gson = new Gson();
            PlaceDto placeDto = gson.fromJson(gson.toJson(result), PlaceDto.class);

            // 변환된 PlaceDto 객체를 사용합니다.
            return placeDto;
        } else {
            // 에러 처리
            throw new RuntimeException("HTTP 요청 실패: " + responseEntity.getStatusCodeValue());
        }
    }

    // 계획 삭제
    @DeleteMapping("/api/{plan_id}")
    ResponseEntity<Map<String, Object>> deletePlan(@PathVariable("plan_id") int plan_id){

        try {
            planService.deletePlan(plan_id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 계획 수정 , 장소 경로 수정
    @PutMapping("/api")
    ResponseEntity<Map<String, Object>> update(@RequestBody Map<String, Object> map){
        System.out.println("put" + map);
        PlanDto planDto = new PlanDto();
        System.out.println(map.get("plan_id"));
        int plan_id = (int) map.get("plan_id");
        planDto.setPlan_id(plan_id);
//        planDto.setUser_id((String) map.get("user_id"));
        planDto.setTitle((String) map.get("title"));
        planDto.setContent((String) map.get("content"));
        planDto.setStart_date((String) map.get("start_date"));
        planDto.setEnd_date((String) map.get("end_date"));
        planDto.setStore((String) map.get("store"));

//        List<String> placeId = (List<String>) map.get("place_id");

        List<Map<String, Object>> plans = (List<Map<String, Object>>) map.get("plans");
        List<String> placeId = new ArrayList<>();

        try {
            planService.updatePlan(planDto);
            int idx = 1;
            planService.userPlanDelete(plan_id);
            for (Map<String, Object> plan : plans) {
                UserPlanDto userPlanDto = new UserPlanDto();
                userPlanDto.setPlan_id(plan_id);
                userPlanDto.setName((String) plan.get("name"));
                userPlanDto.setVicinity((String) plan.get("vicinity"));
                userPlanDto.setPhoto((String) plan.get("photo"));
                userPlanDto.setUrl((String) plan.get("url"));
                userPlanDto.setSequence(idx++);

                placeId.add((String) plan.get("place_id"));
                planService.userPlanMake(userPlanDto);
            }
            idx = 1;
            placeService.deletePlanPlace(plan_id);
            for (String id : placeId) {
                PlanPlaceDto planPlaceDto = new PlanPlaceDto();
                if (!placeService.checkPlaceId(id)){
                    PlaceDto placeDto = detailInfo(id);
                    placeService.placeInfo(placeDto);
                }
                planPlaceDto.setPlan_id(plan_id);
                planPlaceDto.setPlace_id(id);
                planPlaceDto.setSequence(idx++);

                placeService.addPlanPlace(planPlaceDto);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/detail/{plan_id}")
    ResponseEntity<Map<String, Object>> detail(@PathVariable("plan_id") String id){
        Map<String, Object> map = new HashMap<>();
        PlanInfoDto planInfoDto = new PlanInfoDto();
        int plan_id = Integer.parseInt(id);
        try {
            PlanDto planDto = planService.detailByPlanId(plan_id);
            List<String> idList = placeService.placeIdByPlanId(plan_id);
            List<PlaceDto> list = new ArrayList<>();
            for (String place_id : idList) {
                PlaceDto placeDto = placeService.infoByPlaceId(place_id);
                list.add(placeDto);
            }
            planInfoDto.setPlanDto(planDto);
            planInfoDto.setPlaceDtoList(list);

            map.put("planInfo", planInfoDto);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/hit")
    ResponseEntity<Map<String, Object>> updateHit(@RequestBody Map<String, Object> map){

        int plan_id = Integer.parseInt((String) map.get("plan_id"));
        try {
            planService.updateHit(plan_id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/uplike")
    ResponseEntity<Map<String, Object>> upLike(@RequestBody Map<String, Object> map){

        PlanDto planDto = new PlanDto();
        int plan_id = Integer.parseInt((String) map.get("plan_id"));
        String user_id = (String) map.get("user_id");

        planDto.setPlan_id(plan_id);
        planDto.setUser_id(user_id);

        try {
            planService.upLike(plan_id);
            planService.makeLike(planDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/downlike")
    ResponseEntity<Map<String, Object>> downLike(@RequestBody Map<String, Object> map){

        PlanDto planDto = new PlanDto();
        int plan_id = Integer.parseInt((String) map.get("plan_id"));
        String user_id = (String) map.get("user_id");

        planDto.setPlan_id(plan_id);
        planDto.setUser_id(user_id);

        try {
            planService.downLike(plan_id);
            planService.deleteLike(planDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/photo/{place_id}")
    ResponseEntity<Map<String, Object>> getPhotoInfo(@PathVariable("place_id") String id){
        Map<String, Object> map = new HashMap<>();
        PlaceDto placeDto = detailInfo(id);
        try {
            String photo = placeDto.getPhotoreference();
            map.put("photo", photo);
            return new ResponseEntity<>(map, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
