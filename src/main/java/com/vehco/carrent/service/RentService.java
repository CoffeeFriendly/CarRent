package com.vehco.carrent.service;

import com.vehco.carrent.entity.Rent;
import com.vehco.carrent.enums.RentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface RentService {
    Rent create(Long carId, Long userId, LocalDateTime rentStart, LocalDateTime rentEnd);
    Rent findById(Long id);
    List<Rent> findAll();
    Rent updateRent(Long id, Rent updatedRent);
    Rent updateStatus(Long id, RentStatus status);
    Rent delete(Long id);
}
