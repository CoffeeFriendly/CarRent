package com.example.CarRent;

import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabasePreload {
    @Bean
    CommandLineRunner initDatabase(UsersRepository usersRepository) {
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
        };
    }
}
