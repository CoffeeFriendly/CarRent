package com.vehco.carrent.service;

import com.vehco.carrent.enums.RentStatus;
import com.vehco.carrent.entity.Car;
import com.vehco.carrent.entity.Rent;
import com.vehco.carrent.entity.User;
import com.vehco.carrent.repository.RentRepository;
import com.vehco.carrent.utils.EntityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RentServiceImpl implements  RentService{
    private final CarService carService;
    private final UserService userService;
    private final RentRepository rentRepository;

    @Override
    @Transactional
    public Rent create(Long carId, Long userId, LocalDateTime rentStart, LocalDateTime rentEnd) {
        Car car = carService.findById(carId);
        User user = userService.findById(userId);

        // Correct date - end after start, no less than 1 day.
        if (Duration.between(rentStart, rentEnd).toHours() < 24) throw new RuntimeException("Rent length must be at least 1 day");

        Rent rent = new Rent(rentStart, rentEnd, RentStatus.PENDING, user, car);
        rentRepository.save(rent);
        return rent;
    }

    @Override
    public Rent findById(Long id) {
        return rentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found with id " + id));
    }

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    @Transactional
    public Rent updateRent(Long id, Rent updatedRent) {
        Rent rent = findById(id);
        EntityUtil.updateEntity(rent, updatedRent);
        return rent;
    }

    @Override
    @Transactional
    public Rent confirmRent(Long id) {
        Rent rent = findById(id);
        if (rent.getStatus() != RentStatus.PENDING) throw new RuntimeException("Only PENDING rents can be confirmed");
        rent.setStatus(RentStatus.AWAITS);
        return rent;
    }

    @Override
    @Transactional
    public Rent cancelRent(Long id) {
        Rent rent = findById(id);
        if (rent.getStatus() == RentStatus.COMPLETED) throw new RuntimeException("Completed rent can't be cancelled");
        if (rent.getStatus() == RentStatus.CANCELLED) throw new RuntimeException("Rent already cancelled");
        rent.setStatus(RentStatus.CANCELLED);
        return rent;
    }

    @Override
    @Transactional
    public Rent finishRent(Long id) {
        Rent rent = findById(id);
        if (rent.getStatus() != RentStatus.ACTIVE) throw new RuntimeException("Only active rents can be finished");
        rent.setStatus(RentStatus.COMPLETED);
        return rent;
    }

    @Override
    @Transactional
    public Rent startRent(Long id) {
        Rent rent = findById(id);
        if (rent.getStatus() != RentStatus.AWAITS) throw new RuntimeException("Only AWAITS rents can be started");
        rent.setStatus(RentStatus.ACTIVE);
        return rent;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        rentRepository.deleteById(id);
    }
}