package com.example.CarRent.Controller;

import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Exception.RentNotFoundException;
import com.example.CarRent.Repository.RentsRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rents")
public class RentController {
    private final RentsRepository repository;

    public RentController(RentsRepository repository) {this.repository = repository;}

    @GetMapping
    public ResponseEntity getRents() {
        List<RentEntity> rents = repository.findAll();
        return ResponseEntity.ok().body(rents);
    }

    @GetMapping("/{id}")
    public ResponseEntity getRent(@PathVariable Long id) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        return ResponseEntity.ok().body(rent);
    }

    @PostMapping
    public ResponseEntity createRent(@RequestBody RentEntity rent) {
        repository.save(rent);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rent.getId()).toUri()).body(rent);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRent(@PathVariable Long id, @RequestBody RentEntity rent) {
        RentEntity oldRent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        oldRent.setCar(rent.getCar());
        oldRent.setUser(rent.getUser());
        oldRent.setRentStart(rent.getRentStart());
        oldRent.setRentEnd(rent.getRentEnd());
        oldRent.setStatus(rent.getStatus());
        repository.save(oldRent);
        return ResponseEntity.ok().body(oldRent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchRent(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(RentEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, rent, value);
            field.setAccessible(false);
        });
        repository.save(rent);
        return ResponseEntity.ok().body(rent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRent(@PathVariable Long id) {
        RentEntity rent = repository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
        repository.delete(rent);
        return ResponseEntity.ok().body("Rent with id " + id + " has been successfully removed");
    }
}
