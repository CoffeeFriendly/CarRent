package com.vehco.carrent.service;

import com.vehco.carrent.entity.User;
import com.vehco.carrent.repository.UserRepository;
import com.vehco.carrent.utils.EntityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User user = findById(id);
        EntityUtil.updateEntity(user, updatedUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    @Transactional
    public User updateAccountStatus(Long id, boolean isActive) {
        User user = findById(id);
        user.setActive(isActive);
        return user;
    }

    @Override
    @Transactional
    public User delete(Long id) {
        User user = findById(id);
        userRepository.deleteById(id);
        return user;
    }
}