package com.fitness.tracker.service;

import java.util.List;

import com.fitness.tracker.model.WorkoutPlan;

public interface WorkoutPlanService {

    WorkoutPlan create(WorkoutPlan plan);
    List<WorkoutPlan> getAll();
    WorkoutPlan getById(Long id);
    WorkoutPlan update(Long id, WorkoutPlan updatedPlan);
    void delete(Long id);
}
