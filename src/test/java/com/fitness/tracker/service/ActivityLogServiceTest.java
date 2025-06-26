package com.fitness.tracker.service;

import com.fitness.tracker.model.ActivityLog;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutPlan;
import com.fitness.tracker.repository.ActivityLogRepository;
import com.fitness.tracker.service.impl.ActivityLogServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ActivityLogServiceTest {

    private ActivityLogRepository activityLogRepository;
    private ActivityLogService activityLogService;

    @BeforeEach
    void setUp() {
        activityLogRepository = mock(ActivityLogRepository.class);
        activityLogService = new ActivityLogServiceImpl(activityLogRepository);
    }

    @Test
    void testCreateActivityLog() {
        ActivityLog log = new ActivityLog();
        log.setActivity("Running");

        when(activityLogRepository.save(log)).thenReturn(log);

        ActivityLog created = activityLogService.create(log);

        assertNotNull(created);
        assertEquals("Running", created.getActivity());
        verify(activityLogRepository).save(log);
    }

    @Test
    void testGetAllActivityLogs() {
        ActivityLog log1 = new ActivityLog(); log1.setActivity("Cycling");
        ActivityLog log2 = new ActivityLog(); log2.setActivity("Swimming");

        when(activityLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        List<ActivityLog> result = activityLogService.getAll();

        assertEquals(2, result.size());
        assertEquals("Cycling", result.get(0).getActivity());
    }

    @Test
    void testGetById_Found() {
        ActivityLog log = new ActivityLog();
        log.setId(1L);
        log.setActivity("Yoga");

        when(activityLogRepository.findById(1L)).thenReturn(Optional.of(log));

        ActivityLog result = activityLogService.getById(1L);

        assertEquals("Yoga", result.getActivity());
    }

    @Test
    void testGetById_NotFound() {
        when(activityLogRepository.findById(404L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            activityLogService.getById(404L);
        });

        assertEquals("Activity log not found", ex.getMessage());
    }

    @Test
    void testUpdateActivityLog() {
        ActivityLog existing = new ActivityLog();
        existing.setId(1L);
        existing.setActivity("Old");
        existing.setDurationInMinutes(30);
        existing.setDate(LocalDate.now());

        ActivityLog updated = new ActivityLog();
        updated.setActivity("New");
        updated.setDurationInMinutes(45);
        updated.setDate(LocalDate.now().plusDays(1));

        User user = new User(); user.setName("Test User");
        WorkoutPlan plan = new WorkoutPlan(); plan.setName("Plan A");
        updated.setUser(user);
        updated.setWorkoutPlan(plan);

        when(activityLogRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(activityLogRepository.save(existing)).thenReturn(existing);

        ActivityLog result = activityLogService.update(1L, updated);

        assertEquals("New", result.getActivity());
        assertEquals(45, result.getDurationInMinutes());
        assertEquals("Test User", result.getUser().getName());
    }

    @Test
    void testDeleteActivityLog() {
        ActivityLog log = new ActivityLog();
        log.setId(1L);

        when(activityLogRepository.findById(1L)).thenReturn(Optional.of(log));

        activityLogService.delete(1L);

        verify(activityLogRepository).deleteById(1L);
    }

    @Test
    void testDeleteActivityLog_NotFound() {
        when(activityLogRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            activityLogService.delete(999L);
        });

        assertEquals("Activity log not found", ex.getMessage());
    }
}

