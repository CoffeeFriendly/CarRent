package com.example.CarRent.Services;

import com.example.CarRent.DTO.UserDTO;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Exception.UserNotFoundException;
import com.example.CarRent.Mapper.UserMapper;
import com.example.CarRent.Repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    UsersRepository repository;

    public List<UserDTO> getUsers() {
        List<UserEntity> entities = repository.findAll();
        List<UserDTO> users = new ArrayList<UserDTO>();
        for (UserEntity user : entities) {
            users.add(UserMapper.INSTANCE.userToDto(user));
        }
        return users;
    }

    public UserDTO getUser(Long id) {
        UserEntity userEntity = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserDTO user = UserMapper.INSTANCE.userToDto(userEntity);
        return user;
    }

    public UserEntity createUser(UserEntity user) {
        return repository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity newUser) {
        UserEntity user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setFirstName(newUser.getFirstName());
        user.setMidName(newUser.getMidName());
        user.setLastName(newUser.getLastName());
        user.setBirth(newUser.getBirth());
        user.setPassword(newUser.getPassword());
        return repository.save(user);
    }

    public UserEntity patchUser(Long id, Map<Object, Object> fields) {
        UserEntity user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, value);
            field.setAccessible(false);
        });
        return repository.save(user);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
