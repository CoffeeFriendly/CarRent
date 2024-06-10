package com.example.CarRent.Service;

import com.example.CarRent.DTO.UserDTO;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Exception.UserNotFoundException;
import com.example.CarRent.Repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Mock
    UsersRepository usersRepository;
    @Mock
    RentService rentService;
    @InjectMocks
    UserService userService;
    UserEntity user;

    @BeforeEach
    public void setup() {
        user = new UserEntity("Vlad", "Pavlovich", "Litvin", "19-05-1997", "1234567890");
    }

    @Test
    public void userService_whenGetUsers_thenReturnAllUsers() {
        UserEntity user2 = new UserEntity("Vlad", "Pavlovich", "Litvin", "19-05-1997", "1234567890");
        given(usersRepository.findAll()).willReturn(Arrays.asList(user, user2));
        List<UserDTO> userList = userService.getUsersDTO();

        assertEquals(2, userList.size());
    }

    @Test
    public void userService_whenGetUsers_thenReturnEmptyList() {
        given(usersRepository.findAll()).willReturn(Collections.emptyList());
        List<UserDTO> userList = userService.getUsersDTO();
        assertEquals(0, userList.size());
    }

    @Test
    public void userService_whenGetUser_thenReturnUser() {
        given(usersRepository.findById(1L)).willReturn(java.util.Optional.of(user));
        UserDTO userDTO = userService.getUserDTO(1L);
        assertNotNull(userDTO);
    }

    @Test
    public void userService_whenGetUser_thenThrowUserNotFoundException() {
        given(usersRepository.findById(1L)).willReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserDTO(1L));
    }

    @Test
    public void userService_whenCreateUser_thenReturnUser() {
        given(usersRepository.save(user)).willReturn(user);
        user = userService.createUser(user);
        assertNotNull(user);
    }

    @Test
    public void userService_whenUpdateUser_thenReturnUser() {
        given(usersRepository.save(user)).willReturn(user);
        given(usersRepository.findById(1L)).willReturn(java.util.Optional.of(user));
        user.setFirstName("Alexander");
        UserEntity updatedUser = userService.updateUser(1L, user);
        assertEquals("Alexander", updatedUser.getFirstName());
    }

    @Test
    public void userService_whenUpdateUser_thenThrowUserNotFoundException() {
        given(usersRepository.findById(1L)).willReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, user));
    }

    @Test
    public void userService_whenDeleteUser() {
        userService.deleteUser(1L);
        verify(usersRepository, times(1)).deleteById(1L);
    }
}
