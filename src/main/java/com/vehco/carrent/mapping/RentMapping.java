package com.vehco.carrent.mapping;

import com.vehco.carrent.dto.RentDto;
import com.vehco.carrent.entity.Rent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentMapping {
    RentDto toDto(Rent rent);
}
