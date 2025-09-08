package com.vehco.carrent;

import com.vehco.carrent.enums.CarStatus;
import com.vehco.carrent.enums.Color;
import com.vehco.carrent.enums.RentStatus;
import com.vehco.carrent.enums.Role;
import com.vehco.carrent.model.Car;
import com.vehco.carrent.model.Rent;
import com.vehco.carrent.model.User;
import com.vehco.carrent.repository.CarRepository;
import com.vehco.carrent.repository.RentRepository;
import com.vehco.carrent.repository.UserRepository;
import com.vehco.carrent.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class CarRentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentApplication.class, args);
        System.out.println("Hello there!");
    }

    @Bean
    public CommandLineRunner CreateTestData(UserService userService,
                                            CarRepository carRepository,
                                            RentRepository rentRepository) {
        return args -> {
            User user;
            Car car;
            Rent rent;

            try {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashedPassword = encoder.encode("pass123");
                user = new User("Pavlik", null, "pavlikdominator@mail.ru", "Павел",
                        "Морозов", "Григорьевич", "+78005553535", Role.CUSTOMER);

                car = new Car("Lada", "Vesta", 2022, Color.BROWN, "С549МН716RUS", "XTA210990Y1234567",
                        CarStatus.READY, BigDecimal.valueOf(1500), "https://example.com/images/ladaVestaBrown.png");

                LocalDateTime start = LocalDateTime.of(2025, 9, 7, 10, 0); // 07.09.2025 10:00
                LocalDateTime end = LocalDateTime.of(2025, 9, 9, 14, 0);   // 09.09.2025 14:00
                rent = new Rent(start, end, RentStatus.ACTIVE, user, car);

                userService.register(user, "pass123");
                //carRepository.save(car);
                //rentRepository.save(rent);

                System.out.println("\uD83D\uDFE2 ТЕСТОВЫЕ ДАННЫЕ УСПЕШНО СОЗДАНЫ!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        };
    }
}
