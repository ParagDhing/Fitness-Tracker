package com.fitness.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fitness.tracker.model.WorkoutPlan;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
}
