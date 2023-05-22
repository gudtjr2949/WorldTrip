package com.ssafy.trip.service.plan;

import com.ssafy.trip.dto.plan.PlanCommentDto;
import com.ssafy.trip.dto.review.ReviewCommentDto;
import com.ssafy.trip.repository.plan.PlanCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanCommentServiceImpl implements PlanCommentService{

    private PlanCommentRepository planCommentRepository;
    public PlanCommentServiceImpl(PlanCommentRepository planCommentRepository) {
        this.planCommentRepository = planCommentRepository;
    }

    @Override
    public void write(PlanCommentDto planCommentDto) {
        planCommentRepository.write(planCommentDto);
    }
    @Override
    public List<ReviewCommentDto> list(int plan_id) {
        return planCommentRepository.list(plan_id);
    }

    @Override
    public void modify(PlanCommentDto planCommentDto) {
        planCommentRepository.modify(planCommentDto);
    }

    @Override
    public void upLike(int comment_id) {
        planCommentRepository.upLike(comment_id);
    }

    @Override
    public void downLike(int comment_id) {
        planCommentRepository.downLike(comment_id);
    }

    @Override
    public void deleteComment(int comment_id) {
        planCommentRepository.deleteComment(comment_id);
    }

    @Override
    public void delete(PlanCommentDto planCommentDto) {
        planCommentRepository.delete(planCommentDto);
    }

    @Override
    public void make(PlanCommentDto planCommentDto) {
        planCommentRepository.make(planCommentDto);
    }
}
