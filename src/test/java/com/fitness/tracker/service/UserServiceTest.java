package com.fitness.tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fitness.tracker.dto.UserDto;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutPlan;
import com.fitness.tracker.repository.UserRepository;
import com.fitness.tracker.repository.WorkoutPlanRepository;
import com.fitness.tracker.service.impl.UserServiceImpl;

import jakarta.persistence.EntityNotFoundException;

public class UserServiceTest {

    private UserRepository userRepository;
    private WorkoutPlanRepository workoutPlanRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        workoutPlanRepository = mock(WorkoutPlanRepository.class);
        userService = new UserServiceImpl(userRepository, workoutPlanRepository);
    }

    @Test
    void testCreateUserWithValidWorkoutPlan() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(1L);
        workoutPlan.setName("Beginner Plan");

        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.of(workoutPlan));

        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john@example.com");
        userDto.setAge(25);
        userDto.setWorkoutPlan(workoutPlan); // or just set ID and resolve in service

        User user = userDto.castToUser();
        user.setWorkoutPlan(workoutPlan);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User created = userService.create(userDto);

        assertNotNull(created);
        assertEquals("John Doe", created.getName());
        assertEquals("Beginner Plan", created.getWorkoutPlan().getName());

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void testCreateUserWithoutWorkoutPlanShouldFail() {
        when(workoutPlanRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());

        UserDto userDto = new UserDto();
        userDto.setName("Jane Doe");
        userDto.setEmail("jane@example.com");
        userDto.setAge(28);
        userDto.setWorkoutPlan(null); // no plan provided

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            userService.create(userDto);
        });

        assertEquals("Workout plan not found, please create a workout plan and try again", ex.getMessage());
    }
    
    @Test
    void testGetAllUsers() {
        User u1 = new User(); u1.setName("A");
        User u2 = new User(); u2.setName("B");

        when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<User> users = userService.getAll();

        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById_Found() {
        User user = new User();
        user.setId(1L);
        user.setName("Found");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getById(1L);

        assertEquals("Found", result.getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            userService.getById(999L);
        });

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testUpdateUser() {
        User original = new User();
        original.setId(1L);
        original.setName("Old");
        original.setEmail("old@example.com");
        original.setAge(30);

        UserDto userDto = new UserDto();
        userDto.setName("New");
        userDto.setEmail("new@example.com");
        userDto.setAge(35);

        when(userRepository.findById(1L)).thenReturn(Optional.of(original));
        when(userRepository.save(any())).thenReturn(original);

        User result = userService.update(1L, userDto);

        assertEquals("New", result.getName());
        assertEquals("new@example.com", result.getEmail());
        assertEquals(35, result.getAge());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(404L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            userService.delete(404L);
        });

        assertEquals("User not found", ex.getMessage());
    }

}
