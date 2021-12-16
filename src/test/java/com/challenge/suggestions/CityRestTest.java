package com.challenge.suggestions;

import com.challenge.suggestions.endpoints.CityRest;
import com.challenge.suggestions.services.impl.CityServiceImpl;
import com.challenge.suggestions.views.CityView;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito.BDDMyOngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.challenge.suggestions.exceptions.*;
import com.challenge.suggestions.models.City;
import com.challenge.suggestions.persistences.CityRepository;



@WebMvcTest(controllers = CityRest.class)
@ActiveProfiles("test")
public class CityRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityServiceImpl cityService;

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<CityView> cityViewList;

    @BeforeEach
    void setUp() {
        this.cityViewList = new ArrayList<>();
        this.cityViewList.add(new CityView(1L, "Pachuca1", 33.333, 99.999));
        this.cityViewList.add(new CityView(2L, "Pachuca2", 33.333, 99.999));
        this.cityViewList.add(new CityView(3L, "Pachuca3", 33.333, 99.999));
    }

    // mvn clean ; mvn test -Dtest=CityRestTest#shouldCreateNewCity
    @Test
    void shouldCreateNewCity() throws Exception {
 
        given(cityService.createCity(any(CityView.class))).willAnswer((invocation) -> invocation.getArgument(0));

        CityView cV = new CityView(null, "Pachuca", 33.333, 99.999);

        this.mockMvc.perform(post("/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cV)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(cV.getName())))
                .andExpect(jsonPath("$.latitude", is(cV.getLatitude())))
                .andExpect(jsonPath("$.longitude", is(cV.getLongitude())));
                // .andDo(MockMvcResultHandlers.print());

    }


    // mvn clean ; mvn test -Dtest=CityRestTest#shouldReturn400whenCreateNewCity
    @Test
    void shouldReturn400whenCreateNewCity() throws Exception {

        CityView cV = new CityView(null, null, null, null);

        this.mockMvc.perform(post("/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cV)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("campo es obligatorio")))
                .andExpect(jsonPath("$.latitude", is("campo es obligatorio")))
                .andExpect(jsonPath("$.longitude", is("campo es obligatorio")));
                // .andDo(MockMvcResultHandlers.print());

    }


    // mvn clean ; mvn test -Dtest=CityRestTest#shouldReturn409whenCreateNewCity
    @Test
    void shouldReturn409whenCreateNewCity() throws Exception {

        CityView cV = new CityView(1L, "Pachuca", 33.333, 99.999);

        given(cityService.createCity(cV)).willThrow(new ResponseException("Ya existe un registro con este nombre: Pachuca",HttpStatus.CONFLICT));

        this.mockMvc.perform(post("/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cV)))
                .andExpect(status().isConflict());
                // .andDo(MockMvcResultHandlers.print());

    }
}
