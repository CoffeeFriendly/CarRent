package com.example.CarRent.Entity;

import com.example.CarRent.Enums.CarStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cars", schema = "public")
public class CarEntity {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "brand")
    private String brand;
    @NotNull
    @Column(name = "model")
    private String model;
    @NotNull
    @Column(name = "car_year")
    private int carYear;
    @NotNull
    @Column(name = "mileage")
    private int mileage;
    @NotNull
    @Column(name = "daily_price")
    private int dailyPrice;
    @NotNull
    @Column(name = "prepayment")
    private int prepayment;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private CarStatus status;

    // TODO Add car photos

    // TODO Add car plate number

    public CarEntity() {}
    public CarEntity(String brand, String model, int year, int mileage, int dailyPrice, int prepayment,
                     CarStatus status) {
        this.brand = brand;
        this.model = model;
        this.carYear = year;
        this.mileage = mileage;
        this.dailyPrice = dailyPrice;
        this.prepayment = prepayment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return carYear;
    }

    public void setYear(int year) {
        this.carYear = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(int dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public int getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(int prepayment) {
        this.prepayment = prepayment;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }
}