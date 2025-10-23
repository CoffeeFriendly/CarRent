package com.vehco.carrent.controller;

import com.vehco.carrent.dto.RegisterDto;
import com.vehco.carrent.dto.UserDto;
import com.vehco.carrent.entity.User;
import com.vehco.carrent.mapping.UserMappingImpl;
import com.vehco.carrent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMappingImpl userMapping;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping
    List<User> getAllUsers() {
        return userService.findAll();
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
    @PostMapping("/register")
    ResponseEntity<UserDto> registerUser(@RequestBody RegisterDto registerDto) {
        User user = new User(
                registerDto.getUsername(),
                registerDto.getPassword(),
                registerDto.getEmail(),
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getPatronymic(),
                registerDto.getPhone()
        );
        User savedUser = userService.register(user);
        UserDto response = userMapping.toDto(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/{id}/block")
    ResponseEntity<User> blockUser(@PathVariable Long id) {
        User blockedUser = userService.updateAccountStatus(id, false);
        return ResponseEntity.ok(blockedUser);
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/{id}/unblock")
    ResponseEntity<User> unblockUser(@PathVariable Long id) {
        User unblockedUser = userService.updateAccountStatus(id, true);
        return ResponseEntity.ok(unblockedUser);
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<User> deleteAccount(@PathVariable Long id) {
        User deletedUser = userService.delete(id);
        return ResponseEntity.ok(deletedUser);
    }
}