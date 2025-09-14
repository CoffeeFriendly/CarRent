package com.vehco.carrent.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateRentRequest {
    @NotNull
    private Long carId;
    @NotNull
    private Long userId;
    @NotNull
    LocalDateTime rentStart;
    @NotNull
    LocalDateTime rentEnd;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getRentStart() {
        return rentStart;
    }

    public void setRentStart(LocalDateTime rentStart) {
        this.rentStart = rentStart;
    }

    public LocalDateTime getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(LocalDateTime rentEnd) {
        this.rentEnd = rentEnd;
    }
}
