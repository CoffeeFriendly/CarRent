package com.example.CarRent.Controller;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Enums.CarStatus;
import com.example.CarRent.Repository.CarsRepository;
import com.example.CarRent.Service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarsRepository repository;
    private final CarService service;
    CarController(CarsRepository repository, CarService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getCars() {
        List<CarEntity> cars = service.getCars();
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCar(@PathVariable Long id) {
        CarEntity car = service.getCar(id);
        return ResponseEntity.ok().body(car);
    }

    @PostMapping
    public ResponseEntity createCar(@Valid @RequestBody CarEntity newCar) {
        CarEntity car = service.createCar(newCar);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromPath("/{id}").buildAndExpand(car.getId()).toUri()).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCar(@PathVariable Long id, @Valid @RequestBody CarEntity newCar) {
        CarEntity car  = service.updateCar(newCar, id);
        return ResponseEntity.ok().body(car);
    }

    /*
    @PatchMapping("/{id}")
    public ResponseEntity patchCar(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        CarEntity car = service.patchCar(id, fields);
        return ResponseEntity.ok().body(car);
    }
    */

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable Long id) {
        service.deleteCar(id);
        return ResponseEntity.ok().body("Car " + id + " successfully removed");
    }

    @PutMapping("/setToService/{id}")
    public ResponseEntity setCarToService(@PathVariable Long id) {
        service.changeStatus(id, CarStatus.SERVICE);
        return ResponseEntity.ok().body("Car " + id + " successfully set to service");
    }

    @PutMapping("/setToReady/{id}")
    public ResponseEntity setCarToReady(@PathVariable Long id) {
        service.changeStatus(id, CarStatus.READY_FOR_RENT);
        return ResponseEntity.ok().body("Car " + id + " successfully set to ready for rent");
    }
}
