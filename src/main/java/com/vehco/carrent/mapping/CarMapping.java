package com.vehco.carrent.mapping;

import com.vehco.carrent.dto.CarDto;
import com.vehco.carrent.entity.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapping {
    CarDto toDto(Car car);
}
