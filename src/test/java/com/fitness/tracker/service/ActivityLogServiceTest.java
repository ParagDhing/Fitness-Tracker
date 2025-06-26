package com.fitness.tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fitness.tracker.dto.ActivityLogDto;
import com.fitness.tracker.model.ActivityLog;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutPlan;
import com.fitness.tracker.repository.ActivityLogRepository;
import com.fitness.tracker.service.impl.ActivityLogServiceImpl;

import jakarta.persistence.EntityNotFoundException;

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
        ActivityLogDto activityLogDto = new ActivityLogDto();
        activityLogDto.setActivity("Running");
        ActivityLog activityLog = activityLogDto.castToActivityLog();

        when(activityLogRepository.save(any(ActivityLog.class))).thenReturn(activityLog);

        ActivityLog created = activityLogService.create(activityLogDto);

        assertNotNull(created);
        assertEquals("Running", created.getActivity());
        verify(activityLogRepository).save(any(ActivityLog.class));
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
        // Existing entity from DB
        ActivityLog existing = new ActivityLog();
        existing.setId(1L);
        existing.setActivity("Old");
        existing.setDurationInMinutes(30);
        existing.setDate(LocalDate.now());

        // Simulate input DTO (e.g., from controller)
        ActivityLogDto dto = new ActivityLogDto();
        dto.setActivity("New");
        dto.setDurationInMinutes(45);
        dto.setDate(LocalDate.now().plusDays(1));

        // Mock user and workout plan (assume they are required in cast)
        User user = new User();
        user.setName("Test User");

        WorkoutPlan plan = new WorkoutPlan();
        plan.setName("Plan A");

        dto.setUser(user);
        dto.setWorkoutPlan(plan);

        // Convert DTO to ActivityLog (mimicking castToActivityLog)
        ActivityLog updated = dto.castToActivityLog();

        // Mock repository
        when(activityLogRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(activityLogRepository.save(existing)).thenReturn(existing);

        // Call update logic
        ActivityLog result = activityLogService.update(1L, dto);

        // Verify values are updated
        assertEquals("New", result.getActivity());
        assertEquals(45, result.getDurationInMinutes());
        assertEquals("Test User", result.getUser().getName());
        assertEquals("Plan A", result.getWorkoutPlan().getName());
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

