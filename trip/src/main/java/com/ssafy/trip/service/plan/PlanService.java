package com.ssafy.trip.service.plan;

import com.ssafy.trip.dto.plan.PlanDto;
import com.ssafy.trip.dto.plan.UserPlanDto;

import java.util.List;

public interface PlanService {
    List<PlanDto> listByUserId(String user_id); // 사용자의 여행 계획 조회
    PlanDto detailByPlanId(int plan_id); // 여행 계획 아이디로 여행계획 조회
    void makePlan(PlanDto planDto); // 여행 계획 생성
    void updatePlan(PlanDto planDto); // 여행 계획 수정
    void deletePlan(int plan_id); // 여행 계획 삭제
    int getPlanId(String user_id);
    void updateHit(int plan_id);
    void upLike(int plan_id);
    void downLike(int plan_id);

    void makeLike(PlanDto planDto);
    void deleteLike(PlanDto planDto);
    void userPlanMake(UserPlanDto userPlanDto);
    void userPlanUpdate(UserPlanDto userPlanDto);
    void userPlanDelete(int plan_id);

}
