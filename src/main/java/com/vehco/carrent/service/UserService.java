package com.vehco.carrent.service;

import com.vehco.carrent.model.User;

import java.util.List;

public interface UserService {
    User register(User user, String password);
    User updateUser(Long id, User updatedUser);
    User findById(Long id);
    List<User> findAll();
    User updateAccountStatus(Long id, boolean isActive);
    void delete(Long id);
}