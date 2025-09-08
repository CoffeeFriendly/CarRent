package com.vehco.carrent.service;

import com.vehco.carrent.model.Rent;

import java.time.LocalDateTime;
import java.util.List;

public interface RentService {
    Rent create(Long carId, Long userId, LocalDateTime rentStart, LocalDateTime rentEnd);
    Rent findById(Long id);
    List<Rent> findAll();
    Rent updateRent(Long id, Rent updatedRent);
    Rent confirmRent(Long id);
    Rent cancelRent(Long id);
    Rent finishRent(Long id);
    Rent startRent(Long id);
    void delete(Long id);
}
