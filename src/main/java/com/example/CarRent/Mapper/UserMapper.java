package com.example.CarRent.Mapper;

import com.example.CarRent.DTO.UserDTO;
import com.example.CarRent.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToDto(UserEntity user);
}
