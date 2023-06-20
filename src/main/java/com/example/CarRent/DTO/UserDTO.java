package com.example.CarRent.DTO;

import java.time.LocalDate;

public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String midName;
    private String birth;

    public UserDTO() {
        this.birth = String.format("%d-%d-%d", birth.getDayOfMonth(), birth.getMonthValue(), birth.getYear());
    }

    public UserDTO(Long id, String firstName, String lastName, String midName, LocalDate birth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.midName = midName;
        this.birth = String.format("%d-%d-%d", birth.getDayOfMonth(), birth.getMonthValue(), birth.getYear());;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
