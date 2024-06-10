package com.example.CarRent;

import com.example.CarRent.Controller.CarController;
import com.example.CarRent.Controller.RentController;
import com.example.CarRent.Controller.UserController;
import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Enums.CarStatus;
import com.example.CarRent.Enums.RentStatus;
import com.example.CarRent.Repository.CarsRepository;
import com.example.CarRent.Repository.RentsRepository;
import com.example.CarRent.Repository.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabasePreload {
    @Autowired
    UserController userController;
    @Autowired
    CarController carController;
    @Autowired
    RentController rentController;

    @PostConstruct
    void initDatabase() {
        CarEntity car1 = new CarEntity("Lada", "Granta", 2018, 96000, 1500, 500, CarStatus.READY_FOR_RENT);
        CarEntity car2 = new CarEntity("Toyota", "Camry", 2016, 120000, 2500, 1000, CarStatus.SERVICE);
        CarEntity car3 = new CarEntity("Volkswagen", "Polo", 2018, 84500, 2000, 500, CarStatus.READY_FOR_RENT);

        carController.createCar(car1);
        carController.createCar(car2);
        carController.createCar(car3);

        System.out.println("Preloaded cars");

        UserEntity user1 = new UserEntity("Anastasia", "Yakovleva", "Bogdanova",
                "12-05-1989", "asd125");
        UserEntity user2 = new UserEntity("Artem", "Mironov", "Daniilovich",
                "07-08-1975", "qwerty");
        UserEntity user3 = new UserEntity("Vladimir", "Korolev", "Mironovich",
                "26-02-2000", "123123");

        userController.createUser(user1);
        userController.createUser(user2);
        userController.createUser(user3);

        System.out.println("Preloaded users");

        RentEntity rent1 = new RentEntity(user1, car1, LocalDate.now(), LocalDate.now().plusDays(1), RentStatus.ACTIVE, 60);
        RentEntity rent2 = new RentEntity(user2, car2, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), RentStatus.FINISHED, 120);
        RentEntity rent3 = new RentEntity(user3, car3, LocalDate.now().minusDays(1), LocalDate.now(), RentStatus.OVERDUE, 50);

        rentController.createRent(rent1);
        rentController.createRent(rent2);
        rentController.createRent(rent3);

        System.out.println("Preloaded rents");
    }
}
