package com.example.CarRent.Controller;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Enums.CarStatus;
import com.example.CarRent.Service.RentService;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RentControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private RentService rentService;
    @InjectMocks
    private RentController rentController;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    public void testGetRents() throws Exception {
        mockMvc.perform(get("/rents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void testGetRent() throws Exception {
        mockMvc.perform(get("/rents/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @Transactional
    public void testCreateRent() throws Exception {
        UserEntity userEntity = new UserEntity("Дмитрий", "Морозов", "Петрович", "08-06-1997", "1234567890");
        CarEntity carEntity = new CarEntity("Toyota", "Corolla", 2016, 98000, 2500, 1000, CarStatus.READY_FOR_RENT);
        userEntity.setId(1L);
        carEntity.setId(1L);
        RentEntity rentEntity = new RentEntity(userEntity, carEntity, LocalDate.now().minusDays(10000), LocalDate.now().minusDays(9998), 0);

        mockMvc.perform(post("/rents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rentStart").value(LocalDate.now().minusDays(10000).toString()))
                .andExpect(jsonPath("$.rentEnd").value(LocalDate.now().minusDays(9998).toString()));
    }

    @Test
    @Transactional
    public void testUpdateRent() throws Exception {
        UserEntity userEntity = new UserEntity("Дмитрий", "Морозов", "Петрович", "08-06-1997", "1234567890");
        CarEntity carEntity = new CarEntity("Toyota", "Corolla", 2016, 98000, 2500, 1000, CarStatus.READY_FOR_RENT);
        userEntity.setId(1L);
        carEntity.setId(1L);
        RentEntity rentEntity = new RentEntity(userEntity, carEntity, LocalDate.now().minusDays(10000), LocalDate.now().minusDays(9998), 0);

        mockMvc.perform(put("/rents/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentEntity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rentStart").value(LocalDate.now().minusDays(10000).toString()))
                .andExpect(jsonPath("$.rentEnd").value(LocalDate.now().minusDays(9998).toString()));
    }

    /*
    @Test
    public void testPatchRent() throws Exception {
        RentEntity rentEntity = new RentEntity();
        UserEntity userEntity = new UserEntity();
        CarEntity carEntity = new CarEntity();

        carEntity.setId(1L);
        userEntity.setId(1L);
        rentEntity.setId(1L);
        rentEntity.setUser(userEntity);
        rentEntity.setCar(carEntity);

        when(rentService.patchRent(anyLong(), anyMap())).thenReturn(rentEntity);
        mockMvc.perform(patch("/rents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rentEntity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"));
        verify(rentService, times(1)).patchRent(anyLong(), anyMap());
    }
    */

    @Test
    @Transactional
    public void testDeleteRent() throws Exception {
        mockMvc.perform(delete("/rents/1"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testStartRent() throws Exception {
        mockMvc.perform(put("/rents/1/start"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testCancelRent() throws Exception {
        mockMvc.perform(put("/rents/1/cancel"))
                .andExpect(status().isOk());
    }
    @Test
    @Transactional
    public void testFinishRent() throws Exception {
        mockMvc.perform(put("/rents/1/start"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/rents/1/finish"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetCarRentsHistory() throws Exception {
        mockMvc.perform(get("/rents/getCarRentsHistory/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void testGetUserRentsHistory() throws Exception {
        mockMvc.perform(get("/rents/getUserRentsHistory/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}