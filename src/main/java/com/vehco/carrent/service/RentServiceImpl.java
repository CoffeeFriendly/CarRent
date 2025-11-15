package com.vehco.carrent.service;

import com.vehco.carrent.dto.RentDto;
import com.vehco.carrent.enums.CarStatus;
import com.vehco.carrent.enums.RentStatus;
import com.vehco.carrent.entity.Car;
import com.vehco.carrent.entity.Rent;
import com.vehco.carrent.entity.User;
import com.vehco.carrent.mapping.RentMapping;
import com.vehco.carrent.repository.CarRepository;
import com.vehco.carrent.repository.RentRepository;
import com.vehco.carrent.security.UserDetailsImpl;
import com.vehco.carrent.utils.EntityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RentServiceImpl implements  RentService{
    private final CarService carService;
    private final UserService userService;
    private final RentRepository rentRepository;
    private final RentMapping rentMapping;
    private final CarRepository carRepository;

    @Override
    @Transactional
    public RentDto create(Long carId, Long userId, LocalDateTime rentStart, LocalDateTime rentEnd) {
        Car car = carService.findById(carId);
        User user = userService.findById(userId);

        // Correct date - end after start, no less than 1 day.
        if (Duration.between(rentStart, rentEnd).toHours() < 24) throw new RuntimeException("Rent length must be at least 1 day");

        Rent rent = new Rent(rentStart, rentEnd, RentStatus.PENDING, user, car);
        if (!isAvailableAtRentPeriod(rent, car)) throw new RuntimeException("Car is not available for the provided rental period");

        rentRepository.save(rent);
        return rentMapping.toDto(rent);
    }

    @Override
    public RentDto findById(Long id) {
        Rent rentEntity = rentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found with id " + id));
        RentDto rentDto = rentMapping.toDto(rentEntity);
        return rentDto;
    }

    public Rent findEntityById(Long id) {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rent no found with id " + id));
        return rent;
    }

    @Override
    public List<RentDto> findAll() {
        List<RentDto> rentDtos = new ArrayList<>();
        List<Rent> rentEntities = rentRepository.findAll();
        for (Rent rent : rentEntities) {
            rentDtos.add(rentMapping.toDto(rent));
        }
        return rentDtos;
    }

    @Override
    @Transactional
    public RentDto updateRent(Long id, Rent updatedRent) {
        Rent rent = findEntityById(id);
        if (!isAvailableAtRentPeriod(rent, rent.getCar())) throw new RuntimeException("Car is not available for the provided rental period");
        EntityUtil.updateEntity(rent, updatedRent);
        return rentMapping.toDto(rent);
    }

    @Override
    @Transactional
    public RentDto updateStatus(Long id, RentStatus newStatus) {
        Rent rent = findEntityById(id);
        RentStatus currentStatus = rent.getStatus();

        switch (newStatus) {
            case RentStatus.AWAITS:
                if (currentStatus != RentStatus.PENDING) throw new RuntimeException("Only PENDING rents can be confirmed");
                break;
            case RentStatus.CANCELLED:
                if (currentStatus == RentStatus.COMPLETED) throw new RuntimeException("Completed rent can't be cancelled");
                if (currentStatus == RentStatus.CANCELLED) throw new RuntimeException("Rent already cancelled");
                break;
            case RentStatus.COMPLETED:
                if (currentStatus != RentStatus.ACTIVE) throw new RuntimeException("Only active rents can be finished");
                break;
            case RentStatus.ACTIVE:
                if (currentStatus != RentStatus.AWAITS) throw new RuntimeException("Only AWAITS rents can be started");
                break;
        }

        rent.setStatus(newStatus);
        return rentMapping.toDto(rent);
    }

    @Override
    @Transactional
    public RentDto delete(Long id) {
        Rent rent = findEntityById(id);
        rentRepository.deleteById(id);
        return rentMapping.toDto(rent);
    }

    @Override
    public boolean canAccess(Authentication authentication, Long rentId) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl userDetails)) {
            return false;
        }
        return userDetails.getId().equals(findEntityById(rentId).getUser().getId());
    }

    @Override
    public boolean clientCanCancel(Authentication authentication, Long rentId, RentStatus newStatus) {
        Object principal = authentication.getPrincipal();
        Rent rent = findEntityById(rentId);
        if (!(principal instanceof UserDetailsImpl userDetails)) {
            return false;
        }
        if (rent.getStatus() != RentStatus.PENDING && rent.getStatus() != RentStatus.AWAITS) {
            return false;
        }
        if (newStatus != RentStatus.CANCELLED) {
            return false;
        }
        return userDetails.getId().equals(rent.getUser().getId());
    }

    @Override
    public boolean isAvailableAtRentPeriod(Rent rent, Car car) {
        if (car.getStatus() == CarStatus.MAINTENANCE) return false;
        if (rentRepository.existsOverlappingRent(car.getId(), rent.getRentStart(),
                rent.getRentEnd(), rent.getId())) return false;
        return true;
    }
}