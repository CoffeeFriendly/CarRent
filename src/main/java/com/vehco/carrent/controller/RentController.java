package com.vehco.carrent.controller;

import com.vehco.carrent.dto.CreateRentRequest;
import com.vehco.carrent.dto.RentDto;
import com.vehco.carrent.entity.Rent;
import com.vehco.carrent.enums.RentStatus;
import com.vehco.carrent.service.RentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentController {
    final private RentService rentService;

    // TODO: Матрёшка. Сделать DTO.

    @GetMapping
    List<RentDto> getAllRents() {
        return rentService.findAll();
    }

    @GetMapping("/{id}")
    RentDto getRentById(@PathVariable Long id) {
        return rentService.findById(id);
    }

    @PostMapping("")
    RentDto createRent(@RequestBody CreateRentRequest request) {
        RentDto createdRent = rentService.create(request.getCarId(),
                request.getUserId(), request.getRentStart(), request.getRentEnd());
        return createdRent;
    }

    @PutMapping("/{id}")
    ResponseEntity<RentDto> updateRent(@PathVariable Long id, @RequestBody Rent rent) {
        RentDto updatedRent = rentService.updateRent(id, rent);
        return ResponseEntity.ok(updatedRent);
    }

    @PutMapping("/{id}/status")
    ResponseEntity<RentDto> updateRentStatus(@PathVariable Long id, @Valid @RequestBody RentStatus status) {
        RentDto updatedRent = rentService.updateStatus(id, status);
        return ResponseEntity.ok(updatedRent);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<RentDto> deleteRent(@PathVariable Long id) {
        RentDto deletedRent = rentService.delete(id);
        return ResponseEntity.ok(deletedRent);
    }
}
