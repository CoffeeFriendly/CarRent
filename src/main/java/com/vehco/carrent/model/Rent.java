package com.vehco.carrent.model;

import com.vehco.carrent.enums.RentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "rents")
@NoArgsConstructor
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotNull
    @Column(name = "rental_start", nullable = false)
    @DateTimeFormat
    @Getter
    @Setter
    private LocalDateTime rentalStart;

    @NotNull
    @Column(name = "rental_end", nullable = false)
    @DateTimeFormat
    @Getter
    @Setter
    private LocalDateTime rentalEnd;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Getter
    @Setter
    private RentStatus status;

    @NotNull
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    public Rent(LocalDateTime rentalStart, LocalDateTime rentalEnd, RentStatus status, User user, Car car) {
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.status = status;
        this.user = user;
        this.car = car;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", rentalStart=" + rentalStart +
                ", rentalEnd=" + rentalEnd +
                ", status=" + status +
                ", user=" + user +
                ", car=" + car +
                '}';
    }
}
