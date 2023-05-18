package com.ssafy.trip.repository.place;

import com.ssafy.trip.dto.place.PlaceDto;
import com.ssafy.trip.dto.place.PlanPlaceDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceRepository {

    void placeInfo(PlaceDto placeDto); // 장소 기본 정보 입력
    PlaceDto infoByPlaceId(String place_id); // 장소 기본키로 기본, 세부 정보 조회
    List<String> placeIdByPlanId(int plan_id); // 여행 계획에 담겨있는 장소들 조회
    void deletePlanPlace(int plan_id);
    void addPlanPlace(PlanPlaceDto planPlaceDto); // 여행지 경로 설정
    int checkPlaceId(String place_id);



}
