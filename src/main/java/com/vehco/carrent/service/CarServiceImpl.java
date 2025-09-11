package com.vehco.carrent.service;

import com.vehco.carrent.enums.CarStatus;
import com.vehco.carrent.entity.Car;
import com.vehco.carrent.repository.CarRepository;
import com.vehco.carrent.utils.EntityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No car was found with id " + id));
    }

    @Override
    @Transactional
    public Car create(Car car) {
        carRepository.save(car);
        return car;
    }

    @Override
    @Transactional
    public Car update(Long id, Car updatedCar) {
        Car car = findById(id);
        EntityUtil.updateEntity(car, updatedCar);
        return car;
    }

    @Override
    @Transactional
    public Car updateStatus(Long id, CarStatus status) {
        Car car = findById(id);
        car.setStatus(status);
        return car;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}