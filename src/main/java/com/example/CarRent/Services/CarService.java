package com.example.CarRent.Services;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Exception.CarNotFoundException;
import com.example.CarRent.Repository.CarsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class CarService {
    CarsRepository repository;
    public List<CarEntity> getCars() {
        return repository.findAll();
    }

    public CarEntity getCar(Long id) {
        CarEntity car = repository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        return car;
    }

    public CarEntity createCar(CarEntity car) {
        return repository.save(car);
    }

    public CarEntity updateCar(CarEntity newCar, Long id) {
        CarEntity car = repository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        car.setBrand(newCar.getBrand());
        car.setModel(newCar.getModel());
        car.setDailyPrice(newCar.getDailyPrice());
        car.setMileage(newCar.getMileage());
        car.setStatus(newCar.getStatus());
        car.setPrepayment(newCar.getPrepayment());
        car.setYear(newCar.getYear());
        return repository.save(car);
    }

    public CarEntity patchCar(Long id, Map<Object, Object> fields) {
        CarEntity car = repository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(CarEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, car, value);
            field.setAccessible(false);
        });
        return repository.save(car);
    }

    public void deleteCar(Long id) {
        repository.deleteById(id);
    }
}
