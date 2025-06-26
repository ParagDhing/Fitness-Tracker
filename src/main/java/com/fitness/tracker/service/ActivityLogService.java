package com.fitness.tracker.service;

import java.util.List;

import com.fitness.tracker.dto.ActivityLogDto;
import com.fitness.tracker.model.ActivityLog;

public interface ActivityLogService {

    ActivityLog create(ActivityLogDto log);
    List<ActivityLog> getAll();
    ActivityLog getById(Long id);
    ActivityLog update(Long id, ActivityLogDto updatedLog);
    void delete(Long id);
}
