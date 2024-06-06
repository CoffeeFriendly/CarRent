package com.example.CarRent.Services;

import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Exception.RentNotFoundException;
import com.example.CarRent.Repository.RentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class RentService {
    RentsRepository repository;

    public List<RentEntity> getRents () {
        return repository.findAll();
    }
    public RentEntity getRent(Long id) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        return rent;
    }

    public RentEntity createRent(RentEntity rent) {
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

    public void deleteRent(Long id) {
        repository.deleteById(id);
    }
}
