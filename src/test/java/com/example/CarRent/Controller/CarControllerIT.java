package com.example.CarRent.Controller;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Enums.CarStatus;
import com.example.CarRent.Service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO Impove tests

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    public void testGetCars() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void testGetCar() throws Exception {
        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @Transactional
    public void testCreateCar() throws Exception {
        CarEntity newCar = new CarEntity("Toyota", "Corolla", 2016, 98000, 2500, 1000, CarStatus.READY_FOR_RENT);


        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"brand\": \"Toyota\", \"model\": \"Corolla\" , \"year\": 2016, " +
                        "\"mileage\": 98000, \"dailyPrice\": 2500, \"prepayment\": 1000, \"status\": \"READY_FOR_RENT\" }"));
    }

    @Test
    @Transactional
    public void testUpdateCar() throws Exception {
        CarEntity newCar = new CarEntity("Toyota", "Corolla", 2016, 98000, 2500, 1000, CarStatus.READY_FOR_RENT);

        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"brand\": \"Toyota\", \"model\": \"Corolla\" , \"year\": 2016, " +
                        "\"mileage\": 98000, \"dailyPrice\": 2500, \"prepayment\": 1000, \"status\": \"READY_FOR_RENT\" }"));
    }

    /*
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
    */

    @Test
    @Transactional
    public void testDeleteCar() throws Exception {
        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testChangeStatusToService() throws Exception {
        mockMvc.perform(put("/cars/setToService/1"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testChangeStatusToReady() throws Exception {
        mockMvc.perform(put("/cars/setToReady/1"))
                .andExpect(status().isOk());
    }
}