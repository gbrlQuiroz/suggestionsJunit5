package com.challenge.suggestions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import com.challenge.suggestions.models.City;
import com.challenge.suggestions.persistences.*;
import com.challenge.suggestions.views.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CityTest extends SuggestionsTestConfiguration {

    @Autowired
    private CityRepository cityRepository;


    /**
     * Probar que se cargen todos los registros en la base en memoria
     * 
     * mvn clean ; mvn test -Dtest=CityTest#database
     */
    @Test
    public void database() throws Exception {
        long cuenta = cityRepository.count();
        assertEquals(7234, cuenta);
        log.info("Se cuentan registros: {}", cuenta);
    }


    /**
     * Probar POST para crear un registro de City
     * 
     * mvn clean ; mvn test -Dtest=CityTest#postCity
     */
    @Test 
    public void postCity() throws Exception {
        CityView cV = new CityView();
        cV.setName("Pachuca de Soto");
        cV.setLatitude(333.333);
        cV.setLongitude(999.999);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/city").contentType(JSON)
            .content(MAPPER.writeValueAsString(cV)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(MockMvcResultHandlers.print());

    }


    /**
     * Probar POST con error 400
     * 
     * mvn clean ; mvn test -Dtest=CityTest#postCityError400
     */
    @Test 
    public void postCityError400() throws Exception {
        CityView cV = new CityView();
        // cV.setName("Pachuca de Soto");
        // cV.setLatitude(333.333);
        // cV.setLongitude(999.999);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/city").contentType(JSON)
            .content(MAPPER.writeValueAsString(cV)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andDo(MockMvcResultHandlers.print());

    }


    /**
     * Probar POST con error 409
     * 
     * mvn clean ; mvn test -Dtest=CityTest#postCityError409
     */
    @Test 
    public void postCityError409() throws Exception {
        CityView cV = new CityView();
        cV.setName("Beloeil");
        cV.setLatitude(333.333);
        cV.setLongitude(999.999);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/city").contentType(JSON)
            .content(MAPPER.writeValueAsString(cV)))
        .andExpect(MockMvcResultMatchers.status().isConflict())
        .andDo(MockMvcResultHandlers.print());

    }


    /**
     * Probar PUT 
     * 
     * mvn clean ; mvn test -Dtest=CityTest#putCity
     */
    @Test
    @Transactional(readOnly = true) 
    public void putCity() throws Exception {
        CityView cV = new CityView();
        cV.setName("Pachuca");
        cV.setLatitude(333.333);
        cV.setLongitude(999.999);

        Long id = 5882873L;

        City tempo = cityRepository.getById(id);
        log.info("Before--->>>city: {}",tempo);
        assertEquals("Ajax", tempo.getName());

        mockMvc.perform(
            MockMvcRequestBuilders.put("/city/{id}",id).contentType(JSON)
            .content(MAPPER.writeValueAsString(cV)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());

        
        tempo = cityRepository.getById(id);
        log.info("After--->>>city: {}",tempo);
        assertEquals("Pachuca", tempo.getName());

    }

    /**
     * Probar PUT 
     * 
     * mvn clean ; mvn test -Dtest=CityTest#putCityError404
     */
    @Test
    public void putCityError404() throws Exception {
        CityView cV = new CityView();
        cV.setName("Pachuca");
        cV.setLatitude(333.333);
        cV.setLongitude(999.999);

        Long id = 369L;

        mockMvc.perform(
            MockMvcRequestBuilders.put("/city/{id}",id).contentType(JSON)
            .content(MAPPER.writeValueAsString(cV)))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andDo(MockMvcResultHandlers.print());

    }

}
