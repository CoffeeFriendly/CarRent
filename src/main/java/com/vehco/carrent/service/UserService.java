package com.vehco.carrent.service;

import com.vehco.carrent.entity.User;

import java.util.List;

public interface UserService {
    User register(User user);
    User updateUser(Long id, User updatedUser);
    User findById(Long id);
    List<User> findAll();
    User updateAccountStatus(Long id, boolean isActive);
    User delete(Long id);
}