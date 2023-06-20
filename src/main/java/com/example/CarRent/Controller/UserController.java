package com.example.CarRent.Controller;

import com.example.CarRent.DTO.UserDTO;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Exception.UserNotFoundException;
import com.example.CarRent.Mapper.UserMapper;
import com.example.CarRent.Repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UsersRepository repository;
    UserController(UsersRepository repository) { this.repository = repository;}

    @GetMapping
    public ResponseEntity getUsers() {
        List<UserEntity> entities = repository.findAll();
        HashSet<UserDTO> users = new HashSet<UserDTO>();
        for (UserEntity user : entities) {
            users.add(UserMapper.INSTANCE.userToDto(user));
        }
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        UserEntity entity = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserDTO user = UserMapper.INSTANCE.userToDto(entity);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping()
    public ResponseEntity createUser(@Validated @RequestBody UserEntity newUser) {
        repository.save(newUser);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri()).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserEntity newUser) {
        UserEntity oldUser = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setMidName(newUser.getMidName());
        oldUser.setBirth(newUser.getBirth());
        oldUser.setPassword(newUser.getPassword());
        repository.save(oldUser);
        return ResponseEntity.ok().body(oldUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        UserEntity user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, value);
            field.setAccessible(false);
        });

        repository.save(user);

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        if(!repository.existsById(id)) throw new UserNotFoundException(id);
        repository.deleteById(id);
        return ResponseEntity.ok().body("User " + id + " successfully deleted");
    }
}
