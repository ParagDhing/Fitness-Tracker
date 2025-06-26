package com.fitness.tracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutPlan;
import com.fitness.tracker.repository.UserRepository;
import com.fitness.tracker.repository.WorkoutPlanRepository;
import com.fitness.tracker.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    public UserServiceImpl(UserRepository userRepository, WorkoutPlanRepository workoutPlanRepository) {
        this.userRepository = userRepository;
		this.workoutPlanRepository = workoutPlanRepository;
    }

    public User create(User user) {
    	if(user.getWorkoutPlan() == null) {
    		WorkoutPlan workoutPlan = workoutPlanRepository.findFirstByOrderByIdAsc()
    				.orElseThrow(() -> new EntityNotFoundException("Workout plan not found, please create a workout plan and try again"));
    		user.setWorkoutPlan(workoutPlan);
    		
    	}else {
    		workoutPlanRepository.save(user.getWorkoutPlan());
    	}
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User update(Long id, User updatedUser) {
        User user = getById(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setAge(updatedUser.getAge());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
