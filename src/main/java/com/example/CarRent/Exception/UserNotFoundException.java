package com.example.CarRent.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) { super("User with id " + id + " has not been found");}
}
