package com.example.CarRent.Enums;

public enum RentStatus {
    // Main statuses
    WAIT_FOR_APPROVAL,
    WAIT_FOR_PREPAYMENT,
    WAIT_FOR_CLIENT,
    ACTIVE, // means that client took the car
    HANDED_BACK,
    FINISHED,

    // Negative statuses
    REJECTED,
    CANCELLED,
    OVERDUE,

    // Critical statuses
    LOST,
    IN_ACCIDENT,
    BROKEN
}
