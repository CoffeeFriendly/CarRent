package com.vehco.carrent.service;

import com.vehco.carrent.enums.CarStatus;
import com.vehco.carrent.model.Car;

import java.util.List;

public interface CarService {
    List<Car> findAll();
    Car findById(Long id);
    Car create(Car car);
    Car update(Long id, Car updatedCar);
    Car updateStatus(Long id, CarStatus status);
    void delete(Long id);
}
