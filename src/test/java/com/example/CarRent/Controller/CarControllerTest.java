package com.example.CarRent.Controller;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void testGetCars() throws Exception {
        when(carService.getCars()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));

        verify(carService, times(1)).getCars();
    }

    @Test
    public void testGetCar() throws Exception {
        when(carService.getCar(1L)).thenReturn(new CarEntity());

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"));

        verify(carService, times(1)).getCar(1L);
    }

    @Test
    public void testCreateCar() throws Exception {
        CarEntity newCar = new CarEntity();
        newCar.setId(1L);
        newCar.setBrand("Toyota");
        newCar.setModel("Corolla");
        when(carService.createCar(any(CarEntity.class))).thenReturn(newCar);
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"id\": 1, \"brand\": \"Toyota\", \"model\": \"Corolla\" }"));
        verify(carService, times(1)).createCar(any(CarEntity.class));
    }

    @Test
    public void testUpdateCar() throws Exception {
        CarEntity newCar = new CarEntity();
        newCar.setId(1L);
        newCar.setBrand("Toyota");
        newCar.setModel("Corolla");
        when(carService.updateCar(any(CarEntity.class), anyLong())).thenReturn(newCar);
        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"id\": 1, \"brand\": \"Toyota\", \"model\": \"Corolla\" }"));
        verify(carService, times(1)).updateCar(any(CarEntity.class), anyLong());
    }

    @Test
    public void testPatchCar() throws Exception {
        CarEntity newCar = new CarEntity();
        newCar.setId(1L);
        newCar.setBrand("Toyota");
        newCar.setModel("Corolla");
        when(carService.patchCar(anyLong(), any(Map.class))).thenReturn(newCar);
        mockMvc.perform(patch("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"id\": 1, \"brand\": \"Toyota\", \"model\": \"Corolla\" }"));
        verify(carService, times(1)).patchCar(anyLong(), any(Map.class));
    }

    @Test
    public void testDeleteCar() throws Exception {
        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isOk());
        verify(carService, times(1)).deleteCar(1L);
    }
}