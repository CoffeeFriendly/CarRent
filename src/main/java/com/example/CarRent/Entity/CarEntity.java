package com.example.CarRent.Entity;

import com.example.CarRent.Enums.CarStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "cars", schema = "public")
public class CarEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "car_year", nullable = false)
    private int carYear;
    @Column(name = "mileage", nullable = false)
    private int mileage;
    @Column(name = "daily_price", nullable = false)
    private int dailyPrice;
    @Column(name = "prepayment", nullable = false)
    private int prepayment;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
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