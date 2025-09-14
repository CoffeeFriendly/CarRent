package com.vehco.carrent.mapping;

import com.vehco.carrent.dto.UserDto;
import com.vehco.carrent.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapping {
    UserDto toDto(User user);
}