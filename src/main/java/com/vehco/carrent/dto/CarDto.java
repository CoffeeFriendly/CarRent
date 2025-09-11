package com.vehco.carrent.dto;

import com.vehco.carrent.enums.CarStatus;
import com.vehco.carrent.enums.Color;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarDto {
    String brand;
    String model;
    int year;
    Color color;
    CarStatus status;
    int dailyPrice;
    String imageUrl;
}
