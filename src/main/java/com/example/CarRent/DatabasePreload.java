package com.example.CarRent;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Enums.CarStatus;
import com.example.CarRent.Enums.RentStatus;
import com.example.CarRent.Repository.CarsRepository;
import com.example.CarRent.Repository.RentsRepository;
import com.example.CarRent.Repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DatabasePreload {
    @Bean
    CommandLineRunner initDatabase(UsersRepository usersRepository, CarsRepository carsRepository,
                                   RentsRepository rentsRepository) {
        return args -> {
            UserEntity user1 = new UserEntity("Anastasia", "Yakovleva", "Bogdanova",
                    "12-05-1989", "asd125");
            UserEntity user2 = new UserEntity("Artem", "Mironov", "Daniilovich",
                    "07-08-1975", "qwerty");
            UserEntity user3 = new UserEntity("Vladimir", "Korolev", "Mironovich",
                    "26-02-2000", "123123");

            usersRepository.save(user1);
            usersRepository.save(user2);
            usersRepository.save(user3);

            CarEntity car1 = new CarEntity("Lada", "Granta", 2018, 96000, 1500, 500, CarStatus.READY_FOR_RENT);
            CarEntity car2 = new CarEntity("Toyota", "Camry", 2016, 120000, 2500, 1000, CarStatus.SERVICE);
            CarEntity car3 = new CarEntity("Volkswagen", "Polo", 2018, 84500, 2000, 500, CarStatus.READY_FOR_RENT);

            carsRepository.save(car1);
            carsRepository.save(car2);
            carsRepository.save(car3);

            RentEntity rent1 = new RentEntity(user1, car1, LocalDate.now(), LocalDate.now().plusDays(1), RentStatus.ACTIVE);
            RentEntity rent2 = new RentEntity(user2, car2, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), RentStatus.FINISHED);
            RentEntity rent3 = new RentEntity(user3, car3, LocalDate.now().minusDays(1), LocalDate.now(), RentStatus.OVERDUE);

            rentsRepository.save(rent1);
            rentsRepository.save(rent2);
            rentsRepository.save(rent3);
        };
    }
}
