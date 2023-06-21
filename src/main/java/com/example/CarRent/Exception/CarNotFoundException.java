package com.example.CarRent.Exception;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(Long id) {super("Car with id " + id + " has not been found");}
}
