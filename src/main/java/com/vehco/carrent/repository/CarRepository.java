package com.vehco.carrent.repository;

import com.vehco.carrent.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
