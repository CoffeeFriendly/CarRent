package com.example.CarRent.Controller;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Exception.CarNotFoundException;
import com.example.CarRent.Repository.CarsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarsRepository repository;
    CarController(CarsRepository repository) {this.repository = repository;}

    @GetMapping
    public ResponseEntity getCars() {
        List<CarEntity> cars = repository.findAll();
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCar(@PathVariable Long id) {
        CarEntity car = repository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        return ResponseEntity.ok().body(car);
    }

    @PostMapping
    public ResponseEntity createCar(@RequestBody CarEntity car) {
        repository.save(car);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(car.getId()).toUri()).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCar(@PathVariable Long id, CarEntity newCar) {
        CarEntity car  = repository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        car.setBrand(newCar.getBrand());
        car.setModel(newCar.getModel());
        car.setMileage(newCar.getMileage());
        car.setPrepayment(newCar.getPrepayment());
        car.setYear(newCar.getYear());
        car.setStatus(newCar.getStatus());
        car.setDailyPrice(newCar.getDailyPrice());
        repository.save(car);
        return ResponseEntity.ok().body(car);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchCar(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        CarEntity car = repository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(CarEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, car, value);
            field.setAccessible(false);
        });
        repository.save(car);
        return ResponseEntity.ok().body(car);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable Long id) {
        CarEntity car = repository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        repository.delete(car);
        return ResponseEntity.ok().body("Car " + id + " successfully removed");
    }
}
