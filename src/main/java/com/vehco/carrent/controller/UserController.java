package com.vehco.carrent.controller;

import com.vehco.carrent.entity.User;
import com.vehco.carrent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    List<User> getAllUsers() {
        return userService.findAll();
    }
    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
    @PutMapping("/{id}/block")
    ResponseEntity<User> blockUser(@PathVariable Long id) {
        User blockedUser = userService.updateAccountStatus(id, false);
        return ResponseEntity.ok(blockedUser);
    }
    @PutMapping("/{id}/unblock")
    ResponseEntity<User> unblockUser(@PathVariable Long id) {
        User unblockedUser = userService.updateAccountStatus(id, true);
        return ResponseEntity.ok(unblockedUser);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<User> deleteAccount(@PathVariable Long id) {
        User deletedUser = userService.delete(id);
        return ResponseEntity.ok(deletedUser);
    }
}