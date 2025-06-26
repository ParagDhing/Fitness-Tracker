package com.fitness.tracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.fitness.tracker.model.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
