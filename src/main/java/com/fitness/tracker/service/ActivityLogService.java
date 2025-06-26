package com.fitness.tracker.service;

import java.util.List;

import com.fitness.tracker.model.ActivityLog;

public interface ActivityLogService {

    ActivityLog create(ActivityLog log);
    List<ActivityLog> getAll();
    ActivityLog getById(Long id);
    ActivityLog update(Long id, ActivityLog updatedLog);
    void delete(Long id);
}
