package com.fitness.tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.tracker.model.WorkoutPlan;
import com.fitness.tracker.service.impl.WorkoutPlanServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/workout-plans")
public class WorkoutPlanController {

    private final WorkoutPlanServiceImpl workoutPlanService;

    public WorkoutPlanController(WorkoutPlanServiceImpl workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @PostMapping
    public ResponseEntity<WorkoutPlan> create(@Valid @RequestBody WorkoutPlan plan) {
        return ResponseEntity.ok(workoutPlanService.create(plan));
    }

    @GetMapping
    public List<WorkoutPlan> getAll() {
        return workoutPlanService.getAll();
    }

    @GetMapping("/{id}")
    public WorkoutPlan getById(@PathVariable Long id) {
        return workoutPlanService.getById(id);
    }

    @PutMapping("/{id}")
    public WorkoutPlan update(@PathVariable Long id, @Valid @RequestBody WorkoutPlan plan) {
        return workoutPlanService.update(id, plan);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workoutPlanService.delete(id);
    }
}
