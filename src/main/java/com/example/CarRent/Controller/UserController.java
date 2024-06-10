package com.example.CarRent.Controller;

import com.example.CarRent.DTO.UserDTO;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Repository.UsersRepository;
import com.example.CarRent.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UsersRepository repository;
    private final UserService service;
    UserController(UsersRepository repository, UserService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getUsers() {
        List<UserDTO> users = service.getUsersDTO();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        UserDTO user = service.getUserDTO(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity createUser(@Validated @RequestBody UserEntity newUser) {
        UserEntity user = service.createUser(newUser);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromPath("/{id}").buildAndExpand(user.getId()).toUri()).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserEntity newUser) {
        UserEntity user = service.updateUser(id, newUser);
        return ResponseEntity.ok().body(user);
    }

    /*
    @PatchMapping("/{id}")
    public ResponseEntity patchUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        UserEntity user = service.patchUser(id, fields);
        return ResponseEntity.ok().body(user);
    }
     */

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok().body("User " + id + " successfully deleted");
    }
}
