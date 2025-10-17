package com.vehco.carrent.mapping;

import com.vehco.carrent.dto.RentDto;
import com.vehco.carrent.entity.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentMapping {
    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "car", source = "car.id")
    RentDto toDto(Rent rent);
}
