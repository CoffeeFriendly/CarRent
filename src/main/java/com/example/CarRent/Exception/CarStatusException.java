package com.example.CarRent.Exception;

public class CarStatusException extends RuntimeException {
    public CarStatusException(Long id) {
        super("Car with id " + id + " isn't in READY_FOR_RENT status");
    }
}
