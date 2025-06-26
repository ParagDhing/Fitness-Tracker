package com.fitness.tracker.dto;

import com.fitness.tracker.Factory;
import com.fitness.tracker.model.WorkoutPlan;

import jakarta.validation.constraints.NotBlank;

public class WorkoutPlanDto {

	@NotBlank(message = "Name is required")
    private String name;

    private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public WorkoutPlan castToWorkoutPlan() {
		WorkoutPlan workoutPlan = Factory.getWorkoutPlan();
		workoutPlan.setName(name);
		workoutPlan.setDescription(description);
		return workoutPlan;
	}
}
