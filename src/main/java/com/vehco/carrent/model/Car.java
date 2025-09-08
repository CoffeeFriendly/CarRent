package com.vehco.carrent.model;

import com.vehco.carrent.enums.Color;
import com.vehco.carrent.enums.CarStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Entity
@Table(name = "cars")
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(name = "brand", nullable = false, length = 30)
    @Getter
    @Setter
    private String brand;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(name = "model", nullable = false, length = 30)
    @Getter
    @Setter
    private String model;

    @NotNull
    @Min(2000)
    @Max(2024) //TODO: Переделать хардкод на проверку на текущий год
    @Column(name = "car_year", nullable = false)
    @Getter
    @Setter
    private int year;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name="color", nullable = false)
    @Getter
    @Setter
    private Color color;

    @NotBlank
    @Pattern(regexp = "^[А-Я][0-9]{3}[А-Я]{2}[0-9]{3}RUS$",
            message = "Номер должен быть записан в формате Р111СУ116RUS ")
    @Column(name = "license_plate", nullable = false, unique = true, length = 12)
    @Getter
    @Setter
    private String licensePlate;

    @NotBlank
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$", message = "Неверно введён VIN")
    @Column(name = "vin", nullable = false, unique = true, length = 17)
    @Getter
    @Setter
    private String vin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Getter
    @Setter
    private CarStatus status;

    @NotNull
    @Positive
    @Column(name = "daily_price", nullable = false, precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal dailyPrice;

    @URL
    @Column(name = "image")
    @Getter
    @Setter
    private String imageUrl;

    public Car(String brand, String model, int year, Color color, String licensePlate,
               String vin, CarStatus status, BigDecimal dailyPrice, String imageUrl) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.status = status;
        this.dailyPrice = dailyPrice;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", color=" + color +
                ", licensePlate='" + licensePlate + '\'' +
                ", vin='" + vin + '\'' +
                ", status=" + status +
                ", dailyPrice=" + dailyPrice +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}


