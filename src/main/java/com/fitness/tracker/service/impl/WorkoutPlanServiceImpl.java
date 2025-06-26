package com.fitness.tracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.tracker.dto.WorkoutPlanDto;
import com.fitness.tracker.model.WorkoutPlan;
import com.fitness.tracker.repository.WorkoutPlanRepository;
import com.fitness.tracker.service.WorkoutPlanService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService{

    private final WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanServiceImpl(WorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    public WorkoutPlan create(WorkoutPlanDto workoutPlanDto) {
    	WorkoutPlan workoutPlan = workoutPlanDto.castToWorkoutPlan();
        return workoutPlanRepository.save(workoutPlan);
    }

    public List<WorkoutPlan> getAll() {
        return workoutPlanRepository.findAll();
    }

    public WorkoutPlan getById(Long id) {
        return workoutPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout plan not found"));
    }

    public WorkoutPlan update(Long id, WorkoutPlanDto updatedPlan) {
        WorkoutPlan plan = getById(id);
        plan.setName(updatedPlan.getName());
        plan.setDescription(updatedPlan.getDescription());
        return workoutPlanRepository.save(plan);
    }

    public void delete(Long id) {
        workoutPlanRepository.deleteById(id);
    }
}
