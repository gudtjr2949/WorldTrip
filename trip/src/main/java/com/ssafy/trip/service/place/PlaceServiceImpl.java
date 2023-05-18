package com.ssafy.trip.service.place;

import com.ssafy.trip.dto.place.PlaceDto;
import com.ssafy.trip.dto.place.PlanPlaceDto;
import com.ssafy.trip.repository.place.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    private PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }


    @Override
    public void placeInfo(PlaceDto placeDto) {
        placeRepository.placeInfo(placeDto);
    }

    @Override
    public PlaceDto infoByPlaceId(String place_id) {
        return placeRepository.infoByPlaceId(place_id);
    }

    @Override
    public List<String> placeIdByPlanId(int plan_id) {
        return placeRepository.placeIdByPlanId(plan_id);
    }

    @Override
    public void deletePlanPlace(int plan_id) {
        placeRepository.deletePlanPlace(plan_id);
    }

    @Override
    public void addPlanPlace(PlanPlaceDto planPlaceDto) {
        placeRepository.addPlanPlace(planPlaceDto);
    }

    @Override
    public boolean checkPlaceId(String place_id) {
        if (placeRepository.checkPlaceId(place_id) > 0) return true;
        return false;
    }
}
