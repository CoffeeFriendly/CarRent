package com.vehco.carrent.controller;

import com.vehco.carrent.dto.CreateRentRequest;
import com.vehco.carrent.dto.RentDto;
import com.vehco.carrent.entity.Rent;
import com.vehco.carrent.enums.RentStatus;
import com.vehco.carrent.service.RentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentController {
    final private RentService rentService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping
    List<RentDto> getAllRents() {
        return rentService.findAll();
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or @rentServiceImpl.canAccess(authentication, #id)")
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

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    ResponseEntity<RentDto> updateRent(@PathVariable Long id, @RequestBody Rent rent) {
        RentDto updatedRent = rentService.updateRent(id, rent);
        return ResponseEntity.ok(updatedRent);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or " +
            "(@rentServiceImpl.clientCanCancel(authentication, #id, #status))")
    @PutMapping("/{id}/status")
    ResponseEntity<RentDto> updateRentStatus(@PathVariable Long id, @Valid @RequestBody RentStatus status) {
        RentDto updatedRent = rentService.updateStatus(id, status);
        return ResponseEntity.ok(updatedRent);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    ResponseEntity<RentDto> deleteRent(@PathVariable Long id) {
        RentDto deletedRent = rentService.delete(id);
        return ResponseEntity.ok(deletedRent);
    }
}
