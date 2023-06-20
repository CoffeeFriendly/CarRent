package com.example.CarRent.Repository;

import com.example.CarRent.Entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends JpaRepository<CarEntity, Long> {
}
