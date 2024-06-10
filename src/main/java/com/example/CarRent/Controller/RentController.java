package com.example.CarRent.Controller;

import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Exception.CarIsOccupiedException;
import com.example.CarRent.Exception.CarNotFoundException;
import com.example.CarRent.Exception.RentNotFoundException;
import com.example.CarRent.Exception.UserNotFoundException;
import com.example.CarRent.Repository.RentsRepository;
import com.example.CarRent.Service.RentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        RentEntity rent;
        try {
            rent = service.getRent(id);
        } catch (RentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().body(rent);
    }

    @PostMapping
    public ResponseEntity createRent(@RequestBody RentEntity newRent) {
        RentEntity rent;
        try {
            rent = service.createRent(newRent);
        } catch (CarIsOccupiedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.created(ServletUriComponentsBuilder.fromPath("/{id}").buildAndExpand(rent.getId()).toUri()).body(rent);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRent(@PathVariable Long id, @RequestBody RentEntity newRent) {
        RentEntity rent = service.updateRent(newRent, id);
        return ResponseEntity.ok().body(rent);
    }

    /*
    @PatchMapping("/{id}")
    public ResponseEntity patchRent(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        RentEntity rent = service.patchRent(id, fields);
        return ResponseEntity.ok().body(rent);
    }
    */

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRent(@PathVariable Long id) {
        service.deleteRent(id);
        return ResponseEntity.ok().body("Rent with id " + id + " has been successfully removed");
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity cancelRent(@PathVariable Long id) {
        service.cancelRent(id);
        return ResponseEntity.ok().body("Rent with id " + id + " has been successfully canceled");
    }

    @PutMapping("/finish/{id}")
    public ResponseEntity finishRent(@PathVariable Long id) {
        service.finishRent(id);
        return ResponseEntity.ok().body("Rent with id " + id + " has been successfully finished");
    }

    @GetMapping("/getCarRentsHistory/{id}")
    public ResponseEntity getCarRentsHistory(@PathVariable Long id) {
        List<RentEntity> rents;
        try {
            rents = service.getCarRentsHistory(id);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().body(rents);
    }

    @GetMapping("/getUserRentsHistory/{id}")
    public ResponseEntity getUserRentsHistory(@PathVariable Long id) {
        List<RentEntity> rents;
        try {
            rents = service.getUserRentsHistory(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().body(rents);
    }
}
