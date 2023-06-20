package com.example.CarRent;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringToDate {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static LocalDate format(String str) {
        return LocalDate.parse(str, formatter);
    }
}
