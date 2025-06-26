package com.fitness.tracker.service;

import java.util.List;

import com.fitness.tracker.model.User;

public interface UserService {

    User create(User user);
    List<User> getAll();
    User getById(Long id);
    User update(Long id, User user);
    void delete(Long id);
}
