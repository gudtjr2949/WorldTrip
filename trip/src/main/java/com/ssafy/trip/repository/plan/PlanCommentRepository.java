package com.ssafy.trip.repository.plan;

import com.ssafy.trip.dto.plan.PlanCommentDto;
import com.ssafy.trip.dto.review.ReviewCommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanCommentRepository {

    void write(PlanCommentDto planCommentDto);
    List<ReviewCommentDto> list(int plan_id);
    void modify(PlanCommentDto planCommentDto);
    void upLike(int comment_id);
    void downLike(int comment_id);
    void deleteComment(int comment_id);
    void delete(PlanCommentDto planCommentDto);
    void make(PlanCommentDto planCommentDto);



}
