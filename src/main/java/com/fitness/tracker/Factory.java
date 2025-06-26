package com.fitness.tracker;

import com.fitness.tracker.model.ActivityLog;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutPlan;

public class Factory {

	public static WorkoutPlan getWorkoutPlan() {
		return new WorkoutPlan();
	}
	
	public static User getUser() {
		return new User();
	}
	
	public static ActivityLog getActivityLog() {
		return new ActivityLog();
	}
}
