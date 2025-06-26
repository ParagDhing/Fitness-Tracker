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

import com.fitness.tracker.model.ActivityLog;
import com.fitness.tracker.service.impl.ActivityLogServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    private final ActivityLogServiceImpl activityLogService;

    public ActivityLogController(ActivityLogServiceImpl activityLogService) {
        this.activityLogService = activityLogService;
    }

    @PostMapping
    public ResponseEntity<ActivityLog> create(@Valid @RequestBody ActivityLog log) {
        return ResponseEntity.ok(activityLogService.create(log));
    }

    @GetMapping
    public List<ActivityLog> getAll() {
        return activityLogService.getAll();
    }

    @GetMapping("/{id}")
    public ActivityLog getById(@PathVariable Long id) {
        return activityLogService.getById(id);
    }

    @PutMapping("/{id}")
    public ActivityLog update(@PathVariable Long id, @Valid @RequestBody ActivityLog log) {
        return activityLogService.update(id, log);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        activityLogService.delete(id);
    }
}
