package com.fitness.tracker.service;

import java.util.List;

import com.fitness.tracker.dto.WorkoutPlanDto;
import com.fitness.tracker.model.WorkoutPlan;

public interface WorkoutPlanService {

    WorkoutPlan create(WorkoutPlanDto plan);
    List<WorkoutPlan> getAll();
    WorkoutPlan getById(Long id);
    WorkoutPlan update(Long id, WorkoutPlanDto updatedPlan);
    void delete(Long id);
}
