package com.ssafy.trip.service.plan;

import com.ssafy.trip.dto.plan.PlanCommentDto;
import com.ssafy.trip.dto.review.ReviewCommentDto;

import java.util.List;

public interface PlanCommentService {
    void write(PlanCommentDto planCommentDto);
    List<ReviewCommentDto> list(int plan_id);
    void modify(PlanCommentDto planCommentDto);
    void upLike(int comment_id);
    void downLike(int comment_id);
    void deleteComment(int comment_id);
    void delete(PlanCommentDto planCommentDto);
    void make(PlanCommentDto planCommentDto);
}
