package com.example.CarRent.Exception;

public class RentNotFoundException extends RuntimeException {
    public RentNotFoundException(Long id) {super("Rent with id " + id + " has not been found");}
}
