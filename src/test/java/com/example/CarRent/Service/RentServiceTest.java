package com.example.CarRent.Service;

import com.example.CarRent.DTO.UserDTO;
import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Enums.CarStatus;
import com.example.CarRent.Enums.RentStatus;
import com.example.CarRent.Exception.CarIsOccupiedException;
import com.example.CarRent.Exception.CarNotFoundException;
import com.example.CarRent.Exception.RentNotFoundException;
import com.example.CarRent.Exception.UserNotFoundException;
import com.example.CarRent.Repository.CarsRepository;
import com.example.CarRent.Repository.RentsRepository;
import com.example.CarRent.Repository.UsersRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RentServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Mock
    RentsRepository rentsRepository;
    @Mock
    UsersRepository usersRepository;
    @Mock
    CarsRepository carsRepository;
    @Mock
    CarService carService;
    @Mock
    UserService userService;
    @InjectMocks
    RentService rentService;
    RentEntity rent;
    UserEntity user;
    RentEntity rent2 = new RentEntity();
    RentEntity rent3 = new RentEntity();
    CarEntity car;

    @BeforeEach
    public void setup() {
        user = new UserEntity("Vlad", "Pavlovich", "Litvin", "19-05-1997", "1234567890");
        car = new CarEntity("Skoda", "Octavia", 2019, 95000, 2500, 1000, CarStatus.READY_FOR_RENT);
        rent = new RentEntity(user, car, LocalDate.now().minusDays(1), LocalDate.now(), RentStatus.WAIT_FOR_CLIENT, 40);
    }

    @Test
    public void rentService_whenGetRents_thenReturnAllRents() {
        RentEntity rent2 = new RentEntity(user, car, LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), RentStatus.WAIT_FOR_PREPAYMENT, 120);
        given(rentsRepository.findAll()).willReturn(Arrays.asList(rent, rent2));
        List<RentEntity> rentList = rentService.getRents();

        assertEquals(2, rentList.size());
    }

    @Test
    public void rentService_whenGetRents_thenReturnEmptyList() {
        given(rentsRepository.findAll()).willReturn(Collections.emptyList());
        List<RentEntity> rentList = rentService.getRents();
        assertEquals(0, rentList.size());
    }

    @Test
    public void rentService_whenGetRent_thenReturnRent() {
        rentsRepository.save(rent);
        given(rentsRepository.findById(1L)).willReturn(java.util.Optional.of(rent));
        rent = rentService.getRent(1L);
        assertNotNull(rent);
    }

    @Test
    public void rentService_whenGetRent_thenThrowRentNotFoundException() {
        given(rentsRepository.findById(1L)).willReturn(java.util.Optional.empty());
        assertThrows(RentNotFoundException.class, () -> rentService.getRent(1L));
    }

    @Test
    public void rentService_whenCreateRent_thenReturnRent() {
        given(rentsRepository.save(rent)).willReturn(rent);
        rent = rentService.createRent(rent);
        assertNotNull(rent);
    }

    @Test
    public void rentService_whenCreateRent_thenThrowCarIsOccupiedException() {
        given(rentsRepository.findAll()).willReturn(Arrays.asList(rent));
        assertThrows(CarIsOccupiedException.class, () -> rentService.createRent(rent));
    }

    @Test
    public void rentService_whenUpdateRent_thenReturnRent() {
        given(rentsRepository.save(rent)).willReturn(rent);
        given(rentsRepository.findById(1L)).willReturn(java.util.Optional.of(rent));
        rent.setStatus(RentStatus.CANCELLED);
        RentEntity updatedRent = rentService.updateRent(rent, 1L);
        assertEquals(RentStatus.CANCELLED, updatedRent.getStatus());
    }

    @Test
    public void rentService_whenUpdateRent_thenThrowRentNotFoundException() {
        given(rentsRepository.findById(1L)).willReturn(java.util.Optional.empty());
        assertThrows(RentNotFoundException.class, () -> rentService.updateRent(rent, 1L));
    }

    @Test
    public void rentService_whenDeleteRent() {
        rentService.deleteRent(1L);
        verify(rentsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void rentService_whenCancelRent_thenReturnRent() {
        given(rentsRepository.save(rent)).willReturn(rent);
        given(rentsRepository.findById(1L)).willReturn(java.util.Optional.of(rent));
        RentEntity cancelledRent = rentService.cancelRent(1L);
        assertEquals(RentStatus.CANCELLED, cancelledRent.getStatus());
    }

    @Test
    public void rentService_whenFinishRent_thenReturnRent() {
        given(rentsRepository.save(rent)).willReturn(rent);
        given(rentsRepository.findById(1L)).willReturn(java.util.Optional.of(rent));
        RentEntity finishedRent = rentService.finishRent(1L);
        assertEquals(RentStatus.FINISHED, finishedRent.getStatus());
    }

    @Test
    public void rentService_whenGetCarRentsHistory_thenReturnRents() {
        CarEntity car2 = new CarEntity();
        rent2.setCar(car);
        rent3.setCar(car2);

        given(carService.getCar(1L)).willReturn(car);
        given(rentsRepository.findAll()).willReturn(Arrays.asList(rent, rent2, rent3));

        List<RentEntity> rents = rentService.getCarRentsHistory(1L);

        assertEquals(2, rents.size());
    }

    @Test
    public void rentService_whenGetUserRentsHistory_thenReturnRents() {
        UserEntity user2 = new UserEntity();
        rent2.setUser(user);
        rent3.setUser(user2);

        given(userService.getUser(1L)).willReturn(user);
        given(rentsRepository.findAll()).willReturn(Arrays.asList(rent, rent2, rent3));

        List<RentEntity> rentList = rentService.getUserRentsHistory(1L);

        assertEquals(2, rentList.size());
    }
}
