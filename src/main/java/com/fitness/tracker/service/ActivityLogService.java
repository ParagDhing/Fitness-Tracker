package com.fitness.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.tracker.model.ActivityLog;
import com.fitness.tracker.repository.ActivityLogRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public ActivityLog create(ActivityLog log) {
        return activityLogRepository.save(log);
    }

    public List<ActivityLog> getAll() {
        return activityLogRepository.findAll();
    }

    public ActivityLog getById(Long id) {
        return activityLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity log not found"));
    }

    public ActivityLog update(Long id, ActivityLog updatedLog) {
        ActivityLog log = getById(id);
        log.setDate(updatedLog.getDate());
        log.setActivity(updatedLog.getActivity());
        log.setDurationInMinutes(updatedLog.getDurationInMinutes());
        log.setUser(updatedLog.getUser());
        log.setWorkoutPlan(updatedLog.getWorkoutPlan());
        return activityLogRepository.save(log);
    }

    public void delete(Long id) {
    	activityLogRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Activity log not found"));
        activityLogRepository.deleteById(id);
    }
}
