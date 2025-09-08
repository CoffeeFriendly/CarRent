package com.vehco.carrent.repository;

import com.vehco.carrent.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Long> {
}
