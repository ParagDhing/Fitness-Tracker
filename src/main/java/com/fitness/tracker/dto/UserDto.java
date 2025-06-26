package com.fitness.tracker.dto;

import com.fitness.tracker.Factory;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutPlan;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {
	
	@NotBlank(message = "Name is required")
    private String name;

	@Email(message = "Email should be valid")
    private String email;

    private int age;
    
    @NotBlank(message = "WorkoutPlan is required")
    private WorkoutPlan workoutPlan;

	public WorkoutPlan getWorkoutPlan() {
		return workoutPlan;
	}

	public void setWorkoutPlan(WorkoutPlan workoutPlan) {
		this.workoutPlan = workoutPlan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public User castToUser() {
		User user = Factory.getUser();
		user.setName(name);
		user.setAge(age);
		user.setWorkoutPlan(workoutPlan);
		return user;
	}
}
