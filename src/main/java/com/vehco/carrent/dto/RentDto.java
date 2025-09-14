package com.vehco.carrent.dto;

import com.vehco.carrent.enums.RentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentDto {
    LocalDateTime rentStart;
    LocalDateTime rentEnd;
    RentStatus status;
    Long user;
    Long car;
}
