package com.fitness.tracker.dto;

import java.time.LocalDate;

import com.fitness.tracker.Factory;
import com.fitness.tracker.model.ActivityLog;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutPlan;

public class ActivityLogDto {

    private LocalDate date;

    private String activity;

    private int durationInMinutes;

    private User user;

    private WorkoutPlan workoutPlan;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WorkoutPlan getWorkoutPlan() {
		return workoutPlan;
	}

	public void setWorkoutPlan(WorkoutPlan workoutPlan) {
		this.workoutPlan = workoutPlan;
	}
    
	public ActivityLog castToActivityLog() {
		ActivityLog activityLog = Factory.getActivityLog();
		activityLog.setDate(date);
		activityLog.setActivity(activity);
		activityLog.setDurationInMinutes(durationInMinutes);
		activityLog.setUser(user);
		activityLog.setWorkoutPlan(workoutPlan);
		return activityLog;
	}
}
