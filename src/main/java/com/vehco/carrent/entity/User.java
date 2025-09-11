package com.vehco.carrent.entity;

import com.vehco.carrent.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    @Column(name = "username", unique = true, nullable = false)
    @Getter
    @Setter
    private String username;

    @NotBlank
    @Column(name = "password", nullable = false, length = 255)
    @Getter
    @Setter
    private String password;

    @NotBlank
    @Size(min = 10, max = 255)
    @Column(name = "email", unique = true, nullable = false)
    @Getter
    @Setter
    private String email;

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(name = "first_name", nullable = false)
    @Getter
    @Setter
    private String firstName;

    @NotBlank
    @Size(min = 2, max=255)
    @Column(name = "last_name", nullable = false)
    @Getter
    @Setter
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(name = "patronymic", nullable = false)
    @Getter
    @Setter
    private String patronymic;

    @NotBlank
    @Pattern(regexp = "\\+7[0-9]{10}",
            message = "Введите номер в правильном формате (+79252548572)")
    @Column(name = "phone", nullable = false, unique = true)
    @Getter
    @Setter
    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "role", nullable = false)
    @Getter
    @Setter
    private Role role;

    @NotNull
    @Column(name = "is_active", nullable = false)
    @Getter
    @Setter
    private boolean isActive;

    public User(String username, String password, String email, String firstName,
                String lastName, String patronymic, String phone, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phone = phone;
        this.role = role;
        this.isActive = true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                '}';
    }
}
