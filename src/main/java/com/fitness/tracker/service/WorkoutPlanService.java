package com.fitness.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.tracker.model.WorkoutPlan;
import com.fitness.tracker.repository.WorkoutPlanRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    public WorkoutPlan create(WorkoutPlan plan) {
        return workoutPlanRepository.save(plan);
    }

    public List<WorkoutPlan> getAll() {
        return workoutPlanRepository.findAll();
    }

    public WorkoutPlan getById(Long id) {
        return workoutPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout plan not found"));
    }

    public WorkoutPlan update(Long id, WorkoutPlan updatedPlan) {
        WorkoutPlan plan = getById(id);
        plan.setName(updatedPlan.getName());
        plan.setDescription(updatedPlan.getDescription());
        return workoutPlanRepository.save(plan);
    }

    public void delete(Long id) {
        workoutPlanRepository.deleteById(id);
    }
}
