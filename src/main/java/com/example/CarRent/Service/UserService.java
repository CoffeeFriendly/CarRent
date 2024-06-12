package com.example.CarRent.Service;

import com.example.CarRent.DAO.UserDAOimpl;
import com.example.CarRent.DTO.UserDTO;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Exception.UserNotFoundException;
import com.example.CarRent.Mapper.UserMapper;
import com.example.CarRent.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UsersRepository repository;
    @Autowired
    UserDAOimpl userDAO;

    public List<UserDTO> getUsersDTO() {
        List<UserEntity> entities = getUsers();
        List<UserDTO> users = new ArrayList<UserDTO>();
        for (UserEntity user : entities) {
            users.add(UserMapper.INSTANCE.userToDto(user));
        }
        return users;
    }

    public UserDTO getUserDTO(Long id) {
        UserEntity userEntity = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserDTO user = UserMapper.INSTANCE.userToDto(userEntity);
        return user;
    }

    public UserEntity getUser(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<UserEntity> getUsers() {
        return repository.findAll();
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

    /*
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
     */

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
