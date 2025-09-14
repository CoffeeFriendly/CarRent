package com.vehco.carrent.service;

import com.vehco.carrent.dto.RentDto;
import com.vehco.carrent.entity.Rent;
import com.vehco.carrent.enums.RentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface RentService {
    RentDto create(Long carId, Long userId, LocalDateTime rentStart, LocalDateTime rentEnd);
    RentDto findById(Long id);
    List<RentDto> findAll();
    RentDto updateRent(Long id, Rent updatedRent);
    RentDto updateStatus(Long id, RentStatus status);
    RentDto delete(Long id);
}
