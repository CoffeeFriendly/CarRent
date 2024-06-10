package com.example.CarRent.Service;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Enums.CarStatus;
import com.example.CarRent.Exception.CarNotFoundException;
import com.example.CarRent.Repository.CarsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Mock
    CarsRepository carsRepository;
    @Mock
    RentService rentService;
    @InjectMocks
    CarService carService;
    CarEntity car;

    @BeforeEach
    public void setup() {
        car = new CarEntity("Skoda", "Octavia", 2019, 95000, 2500, 1000, CarStatus.READY_FOR_RENT);
    }


    @Test
    public void carService_whenGetCars_thenReturnAllCars() {
        CarEntity car2 = new CarEntity("Fiat", "Albea", 2016, 140000, 1500, 500, CarStatus.READY_FOR_RENT);
        given(carsRepository.findAll()).willReturn(Arrays.asList(car, car2));
        List<CarEntity> carList = carService.getCars();

        assertEquals(2, carList.size());
    }

    @Test
    public void carService_whenGetCars_thenReturnEmptyList() {
        given(carsRepository.findAll()).willReturn(Collections.emptyList());
        List<CarEntity> carList = carService.getCars();
        assertEquals(0, carList.size());
    }

    @Test
    public void carService_whenGetCar_thenReturnCar() {
        carsRepository.save(car);
        given(carsRepository.findById(1L)).willReturn(java.util.Optional.of(car));
        car = carService.getCar(1L);
        assertNotNull(car);
    }

    @Test
    public void carService_whenGetCar_thenThrowCarNotFoundException() {
        given(carsRepository.findById(1L)).willReturn(java.util.Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.getCar(1L));
    }

    @Test
    public void carService_whenCreateCar_thenReturnCar() {
        given(carsRepository.save(car)).willReturn(car);
        car = carService.createCar(car);
        assertNotNull(car);
    }

    @Test
    public void carService_whenUpdateCar_thenReturnCar() {
        given(carsRepository.save(car)).willReturn(car);
        given(carsRepository.findById(1L)).willReturn(java.util.Optional.of(car));
        car.setModel("Superb");
        CarEntity updatedCar = carService.updateCar(car, 1L);
        assertEquals("Superb", updatedCar.getModel());
    }

    @Test
    public void carService_whenUpdateCar_thenThrowCarNotFoundException() {
        given(carsRepository.findById(1L)).willReturn(java.util.Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.updateCar(car, 1L));
    }

    @Test
    public void carService_whenDeleteCar() {
        carService.deleteCar(1L);
        verify(carsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void carService_whenAddMileage_thenReturnCar() {
        given(carsRepository.findById(1L)).willReturn(java.util.Optional.of(car));
        given(carsRepository.save(car)).willReturn(car);
        carService.addMileage(1L, 1000);
        verify(carsRepository, times(1)).findById(1L);
        verify(carsRepository, times(1)).save(car);
        assertEquals(96000, car.getMileage());
    }

    @Test
    public void carService_whenAddMileage_thenThrowCarNotFoundException() {
        given(carsRepository.findById(1L)).willReturn(java.util.Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.addMileage(1L, 1000));
    }

    @Test
    public void carService_whenChangeStatus_thenReturnCar() {
        given(carsRepository.findById(1L)).willReturn(java.util.Optional.of(car));
        given(carsRepository.save(car)).willReturn(car);
        carService.changeStatus(1L, CarStatus.READY_FOR_RENT);
        verify(carsRepository, times(1)).findById(1L);
        verify(carsRepository, times(1)).save(car);
        assertEquals(CarStatus.READY_FOR_RENT, car.getStatus());
    }

    @Test
    public void carService_whenChangeStatus_thenThrowCarNotFoundException() {
        given(carsRepository.findById(1L)).willReturn(java.util.Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.changeStatus(1L, CarStatus.READY_FOR_RENT));
    }
}