package com.vehco.carrent.dto;

import com.vehco.carrent.enums.CarStatus;
import com.vehco.carrent.enums.Color;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCarRequest {
    private String brand;
    private String model;
    private int year;
    private Color color;
    private String licensePlate;
    private String vin;
    private CarStatus status;
    private BigDecimal dailyPrice;
    private String imageUrl;
}
