package com.example.CarRent.Entity;

import com.example.CarRent.StringToDate;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "users", schema = "public")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "mid_name", nullable = false)
    private String midName;
    @Column(name = "birth", nullable = false)
    private LocalDate birth;
    @Column(name = "password", nullable = false)
    private String password;

    public UserEntity() {}
    public UserEntity(String firstName, String lastName, String midName, String birth,
                      String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.midName = midName;
        this.birth = StringToDate.format(birth);
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
