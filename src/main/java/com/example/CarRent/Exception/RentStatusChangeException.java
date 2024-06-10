package com.example.CarRent.Exception;

import com.example.CarRent.Enums.RentStatus;

public class RentStatusChangeException extends RuntimeException {
    public RentStatusChangeException(Long id, RentStatus from, RentStatus to) {
        super("Fail to change status for rent with id " + id
        + ", rent can not be changed from " + from + " to " + to);
    }
}
