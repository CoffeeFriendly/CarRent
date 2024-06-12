package com.example.CarRent.Entity;

import com.example.CarRent.Enums.RentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "rents", schema = "public")
public class RentEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name="car_id")
    private CarEntity car;
    @Column(name = "rent_start", nullable = false)
    private LocalDate rentStart;
    @Column(name = "rent_end", nullable = false)
    private LocalDate rentEnd;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RentStatus status;
    @Column(name = "mileage", nullable = false)
    private int mileage;

    public RentEntity() {}

    public RentEntity(UserEntity user, CarEntity car, LocalDate rentStart, LocalDate rentEnd, int mileage) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        this.status = RentStatus.WAIT_FOR_CLIENT;
        this.mileage = mileage;
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

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setStatus(RentStatus status) {
        this.status = status;
    }
}
