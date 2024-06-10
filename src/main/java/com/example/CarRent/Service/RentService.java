package com.example.CarRent.Service;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Enums.CarStatus;
import com.example.CarRent.Enums.RentStatus;
import com.example.CarRent.Exception.CarIsOccupiedException;
import com.example.CarRent.Exception.RentNotFoundException;
import com.example.CarRent.Repository.RentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentService {
    @Autowired
    RentsRepository repository;
    @Autowired
    CarService carService;
    @Autowired
    UserService userService;

    public List<RentEntity> getRents () {
        return repository.findAll();
    }
    public RentEntity getRent(Long id) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        return rent;
    }

    public RentEntity createRent(RentEntity rent) {
        RentEntity overlapingRent = isCarFreeToRentAtPeriod(rent);
        if (overlapingRent != null) {
            throw new CarIsOccupiedException(rent, overlapingRent);
        }
        return repository.save(rent);
    }

    public RentEntity updateRent(RentEntity newRent, Long id) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        rent.setRentStart(newRent.getRentStart());
        rent.setRentEnd(newRent.getRentEnd());
        rent.setCar(newRent.getCar());
        rent.setUser(newRent.getUser());
        rent.setStatus(newRent.getStatus());
        return repository.save(rent);
    }

    /*
    public RentEntity patchRent(Long id, Map<Object, Object> fields) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(RentEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, rent, value);
            field.setAccessible(false);
        });
        return repository.save(rent);
    }
    */

    public void deleteRent(Long id) {
        repository.deleteById(id);
    }

    public RentEntity cancelRent(Long id) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        rent.setStatus(RentStatus.CANCELLED);
        return repository.save(rent);
    }

    public RentEntity finishRent(Long id) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        CarEntity car = rent.getCar();

        rent.setStatus(RentStatus.FINISHED);
        carService.addMileage(car.getId(), rent.getMileage());
        carService.changeStatus(car.getId(), CarStatus.SERVICE);

        return repository.save(rent);
    }

    /**
     * Determines if a car is free to rent within a specific period.
     *
     * @param  rent the RentEntity representing the rental period to be checked
     * @return      the RentEntity overlapping with the rental period, or null if the car is free
     */
    public RentEntity isCarFreeToRentAtPeriod( RentEntity rent) {
        LocalDate rentStart = rent.getRentStart();
        LocalDate rentEnd = rent.getRentEnd();
        CarEntity car = rent.getCar();

        List<RentEntity> rentsToCheck = repository.findAll();
        RentEntity overlapingRent = rentsToCheck.stream()
                .filter(r -> r.getCar().getId() == rent.getCar().getId())
                .filter(r -> rentStart.isBefore(r.getRentEnd()) && rentEnd.isAfter(r.getRentStart()))
                .filter(r -> rent.getStatus() != RentStatus.CANCELLED && r.getStatus() != RentStatus.FINISHED)
                .findFirst()
                .orElse(null);
        return overlapingRent;
    }

    public List<RentEntity> getCarRentsHistory(Long id) {
        CarEntity car = carService.getCar(id);
        List<RentEntity> rents = repository.findAll().stream()
                .filter(r -> r.getCar() == car)
                .collect(Collectors.toList());
        return rents;
    }

    public List<RentEntity> getUserRentsHistory(Long id) {
        UserEntity user = userService.getUser(id);
        List<RentEntity> rents = repository.findAll().stream()
                .filter(r -> r.getUser() == user)
                .collect(Collectors.toList());
        return rents;
    }
}
