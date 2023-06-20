package com.example.CarRent.Repository;

import com.example.CarRent.Entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentsRepository extends JpaRepository<RentEntity, Long> {
}
