package com.vehco.carrent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phone;
}
