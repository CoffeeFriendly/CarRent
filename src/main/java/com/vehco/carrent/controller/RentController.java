package com.vehco.carrent.controller;

import com.vehco.carrent.entity.Rent;
import com.vehco.carrent.enums.RentStatus;
import com.vehco.carrent.service.RentService;
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
    List<Rent> getAllRents() {
        return rentService.findAll();
    }

    @GetMapping("/{id}")
    Rent getRentById(@PathVariable Long id) {
        return rentService.findById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Rent> updateRent(@PathVariable Long id, @RequestBody Rent rent) {
        Rent updatedRent = rentService.updateRent(id, rent);
        return ResponseEntity.ok(updatedRent);
    }

    @PutMapping("/{id}/status")
    ResponseEntity<Rent> updateRentStatus(@PathVariable Long id, @RequestBody RentStatus status) {
        Rent updatedRent = rentService.updateStatus(id, status);
        return ResponseEntity.ok(updatedRent);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Rent> deleteRent(@PathVariable Long id) {
        Rent deletedRent = rentService.delete(id);
        return ResponseEntity.ok(deletedRent);
    }
}
