package com.fitness.tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fitness.tracker.model.WorkoutPlan;
import com.fitness.tracker.repository.WorkoutPlanRepository;
import com.fitness.tracker.service.impl.WorkoutPlanServiceImpl;

import jakarta.persistence.EntityNotFoundException;

class WorkoutPlanServiceImplTest {

    private WorkoutPlanRepository workoutPlanRepository;
    private WorkoutPlanServiceImpl workoutPlanService;

    @BeforeEach
    void setUp() {
        workoutPlanRepository = mock(WorkoutPlanRepository.class);
        workoutPlanService = new WorkoutPlanServiceImpl(workoutPlanRepository);
    }

    @Test
    void testCreate() {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setName("Plan A");

        when(workoutPlanRepository.save(plan)).thenReturn(plan);

        WorkoutPlan created = workoutPlanService.create(plan);
        assertEquals("Plan A", created.getName());
        verify(workoutPlanRepository, times(1)).save(plan);
    }

    @Test
    void testGetAll() {
        List<WorkoutPlan> plans = Arrays.asList(new WorkoutPlan(), new WorkoutPlan());

        when(workoutPlanRepository.findAll()).thenReturn(plans);

        List<WorkoutPlan> result = workoutPlanService.getAll();
        assertEquals(2, result.size());
        verify(workoutPlanRepository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setId(1L);

        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.of(plan));

        WorkoutPlan found = workoutPlanService.getById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void testGetById_NotFound() {
        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workoutPlanService.getById(1L));
    }

    @Test
    void testUpdate() {
        WorkoutPlan existing = new WorkoutPlan();
        existing.setId(1L);
        existing.setName("Old Plan");
        existing.setDescription("Old Description");

        WorkoutPlan updated = new WorkoutPlan();
        updated.setName("New Plan");
        updated.setDescription("New Description");

        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(workoutPlanRepository.save(any())).thenReturn(existing);

        WorkoutPlan result = workoutPlanService.update(1L, updated);

        assertEquals("New Plan", result.getName());
        assertEquals("New Description", result.getDescription());
    }

    @Test
    void testDelete() {
        doNothing().when(workoutPlanRepository).deleteById(1L);
        workoutPlanService.delete(1L);
        verify(workoutPlanRepository, times(1)).deleteById(1L);
    }
}

