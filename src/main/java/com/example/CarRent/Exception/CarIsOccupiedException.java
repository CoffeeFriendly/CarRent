package com.example.CarRent.Exception;

import com.example.CarRent.Entity.RentEntity;

public class CarIsOccupiedException extends RuntimeException {
    public CarIsOccupiedException(RentEntity failedRent, RentEntity occupiedRent) {super("Failed to book rent of car with id "
            + failedRent.getCar().getId() + " on date from " + failedRent.getRentStart() + " to " + failedRent.getRentEnd()
            + " because car with id " + occupiedRent.getCar().getId() + " is already booked on date from "
            + occupiedRent.getRentStart() + " to " + occupiedRent.getRentEnd() + "\nId of booked rent: " + occupiedRent.getId());}
}
