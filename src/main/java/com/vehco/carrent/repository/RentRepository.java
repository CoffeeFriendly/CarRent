package com.vehco.carrent.repository;

import com.vehco.carrent.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    @Query("""
    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
    FROM Rent r
    WHERE r.car.id = :carId
      AND r.status NOT IN (com.vehco.carrent.enums.RentStatus.CANCELLED,
                           com.vehco.carrent.enums.RentStatus.COMPLETED)
      AND (:excludeRentId IS NULL OR r.id <> :excludeRentId)
      AND r.rentStart < :rentEnd
      AND r.rentEnd > :rentStart
    """)
    boolean existsOverlappingRent(@Param("carId") Long carId,
                                  @Param("rentStart") LocalDateTime rentStart,
                                  @Param("rentEnd") LocalDateTime rentEnd,
                                  @Param("excludeRentId") Long excludeRentId);
}
