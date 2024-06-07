package com.example.CarRent.Controller;

import com.example.CarRent.Entity.CarEntity;
import com.example.CarRent.Entity.RentEntity;
import com.example.CarRent.Entity.UserEntity;
import com.example.CarRent.Service.RentService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private RentService rentService;
    @InjectMocks
    private RentController rentController;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(rentController).build();
    }

    @Test
    public void testGetRents() throws Exception {
        when(rentService.getRents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/rents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));

        verify(rentService, times(1)).getRents();
    }

    @Test
    public void testGetRent() throws Exception {
        when(rentService.getRent(1L)).thenReturn(new RentEntity());

        mockMvc.perform(get("/rents/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"));

        verify(rentService, times(1)).getRent(1L);
    }

    @Test
    public void testCreateRent() throws Exception {
        RentEntity rentEntity = new RentEntity();
        CarEntity carEntity = new CarEntity();
        UserEntity userEntity = new UserEntity();

        carEntity.setId(1L);
        userEntity.setId(1L);
        rentEntity.setId(1L);
        rentEntity.setUser(userEntity);
        rentEntity.setCar(carEntity);

        when(rentService.createRent(any(RentEntity.class))).thenReturn(rentEntity);
        mockMvc.perform(post("/rents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"));

        verify(rentService, times(1)).createRent(any(RentEntity.class));
    }

    @Test
    public void testUpdateRent() throws Exception {
        RentEntity rentEntity = new RentEntity();
        UserEntity userEntity = new UserEntity();
        CarEntity carEntity = new CarEntity();

        carEntity.setId(1L);
        userEntity.setId(1L);
        rentEntity.setId(1L);
        rentEntity.setUser(userEntity);
        rentEntity.setCar(carEntity);

        when(rentService.updateRent(any(RentEntity.class), anyLong())).thenReturn(rentEntity);
        mockMvc.perform(put("/rents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rentEntity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"));
        verify(rentService, times(1)).updateRent(any(RentEntity.class), anyLong());
    }

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

    @Test
    public void testDeleteRent() throws Exception {
        mockMvc.perform(delete("/rents/1"))
                .andExpect(status().isOk());
        verify(rentService, times(1)).deleteRent(1L);
    }
}
