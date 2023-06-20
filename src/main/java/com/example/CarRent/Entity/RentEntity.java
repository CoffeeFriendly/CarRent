package com.example.CarRent.Entity;

import com.example.CarRent.Enums.RentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RentEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name="car_id")
    private CarEntity car;
    private LocalDate rentStart;
    private LocalDate rentEnd;
    private RentStatus status;

    public RentEntity() {}

    public RentEntity(UserEntity user, CarEntity car, LocalDate rentStart, LocalDate rentEnd, RentStatus status) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

    public LocalDate getRentStart() {
        return rentStart;
    }

    public void setRentStart(LocalDate rentStart) {
        this.rentStart = rentStart;
    }

    public LocalDate getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(LocalDate rentEnd) {
        this.rentEnd = rentEnd;
    }

    public RentStatus getStatus() {
        return status;
    }

    public void setStatus(RentStatus status) {
        this.status = status;
    }
}
