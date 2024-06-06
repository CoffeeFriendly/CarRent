package com.example.CarRent.Controller;

import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Exception.RentNotFoundException;
import com.example.CarRent.Repository.RentsRepository;
import com.example.CarRent.Services.RentService;
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
    private final RentService service;

    public RentController(RentsRepository repository, RentService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getRents() {
        List<RentEntity> rents = service.getRents();
        return ResponseEntity.ok().body(rents);
    }

    @GetMapping("/{id}")
    public ResponseEntity getRent(@PathVariable Long id) {
        RentEntity rent = service.getRent(id);
        return ResponseEntity.ok().body(rent);
    }

    @PostMapping
    public ResponseEntity createRent(@RequestBody RentEntity newRent) {
        RentEntity rent = service.createRent(newRent);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rent.getId()).toUri()).body(rent);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRent(@PathVariable Long id, @RequestBody RentEntity newRent) {
        RentEntity rent = service.updateRent(newRent, id);
        return ResponseEntity.ok().body(rent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchRent(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        RentEntity rent = service.patchRent(id, fields);
        return ResponseEntity.ok().body(rent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRent(@PathVariable Long id) {
        service.deleteRent(id);
        return ResponseEntity.ok().body("Rent with id " + id + " has been successfully removed");
    }
}
