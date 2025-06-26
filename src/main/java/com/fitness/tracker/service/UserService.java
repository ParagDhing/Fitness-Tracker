package com.fitness.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.tracker.model.User;
import com.fitness.tracker.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User update(Long id, User updatedUser) {
        User user = getById(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setAge(updatedUser.getAge());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
