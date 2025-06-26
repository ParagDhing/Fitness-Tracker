package com.fitness.tracker.service;

import java.util.List;

import com.fitness.tracker.dto.UserDto;
import com.fitness.tracker.model.User;

public interface UserService {

    User create(UserDto user);
    List<User> getAll();
    User getById(Long id);
    User update(Long id, UserDto user);
    void delete(Long id);
}
