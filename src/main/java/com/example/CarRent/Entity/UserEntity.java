package com.example.CarRent.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String midName;
    private LocalDate birth;

    // TODO add driver license photo

    public UserEntity() {}
    public UserEntity(Long id, String firstName, String lastName, String midName, LocalDate birth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.midName = midName;
        this.birth = birth;
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

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
