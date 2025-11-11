package com.vehco.carrent.controller;

import com.vehco.carrent.dto.CarDto;
import com.vehco.carrent.dto.CreateCarRequest;
import com.vehco.carrent.entity.Car;
import com.vehco.carrent.enums.CarStatus;
import com.vehco.carrent.mapping.CarMapping;
import com.vehco.carrent.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final CarMapping carMapping;

    @GetMapping
    List<Car> getAllCars() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    Car getCarById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PostMapping()
    ResponseEntity<CarDto> createCar(@RequestBody CreateCarRequest request) {
        Car createdCar = carService.create(Car.from(request));
        CarDto carDto = carMapping.toDto(createdCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(carDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<Car> updateCar(@RequestBody Car car, @PathVariable Long id) {
        Car updatedCar = carService.update(id, car);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCar);
    }

    @PutMapping("/{id}/status")
    ResponseEntity<Car> updateCarStatus(@PathVariable Long id, @Valid @RequestBody CarStatus status) {
        Car updatedCar = carService.updateStatus(id, status);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Car> deleteCar(@PathVariable Long id) {
        Car deletedCar = carService.delete(id);
        return ResponseEntity.ok(deletedCar);
    }
}
