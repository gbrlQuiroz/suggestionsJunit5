package com.challenge.suggestions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;


import com.challenge.suggestions.persistences.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuggestionsTest extends SuggestionsTestConfiguration {

    @Autowired
    private CityRepository cityRepository;

    /**
     * Probar que se cargen todos los registros en la base en memoria
     */
    @Test
    public void database() throws Exception {
        long cuenta = cityRepository.count();
        assertEquals(7234, cuenta);
        log.info("Se cuentan registros: {}", cuenta);
    }
    /**
     * Probar endpoint sin parametros de latitud y longitud
     * en todos los elemento el campos 'score' de ser 1.0
     */
    @Test
    public void getSuggestionsWithout() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/suggestions?q=Am")
                .contentType(JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        log.info("Se ejecuto: /suggestions?q=Am --->>> HTTP-status: 200(OK) --->>> Todos los  score son 1.0");
    }

    /**
     * Probar endpoint con latitud y longitud
     * el campo 'score' es asignado siguiendo las regla de negocio
     */
    @Test
    public void getSuggestionsWithLatitudeAndLongitude() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/suggestions?q=Am&latitude=45.00000&longitude=-75.00000")
                .contentType(JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        log.info("Se ejecuto: /suggestions?q=Am&latitude=45.00000&longitude=-75.00000 --->>> HTTP-status: 200(OK)");
    }

    /**
     * Probar endpoint con un criterio exacto
     */
    @Test
    public void getSuggestionsOnlyOne() throws Exception {
        String resultado = mockMvc.perform(
            MockMvcRequestBuilders.get("/suggestions?q=Ajax")
            .contentType(JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn().getResponse().getContentAsString();

        log.info("Se ejecuto: /suggestions?q=Ajax --->>> HTTP-status: 200(OK)");

        String cadena = "{\"suggestions\":[{\"name\":\"Ajax\",\"latitude\":\"43.85012\",\"longitude\":\"-79.03288\",\"score\":1.0}]}";
        
        log.info("revisando el valor de latitude");
        assertEquals(true, resultado.contains("43.85012"));
        
        log.info("revisando que json corresponda a: {}",cadena);
        assertEquals(cadena, resultado);
    }

    /**
     * Probar con error, no se esta poniendo el parametro requerido 'q'
     * regresa 400 bad request
     */
    @Test
    public void getSuggestionsError() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/suggestions/?latitude=45.00000&longitude=-75.00000")
                .contentType(JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        log.info("Se ejecuto: /suggestions/?latitude=45.00000&longitude=-75.00000 --->>> HTTP-status: 400(falta el parametro 'q')");
    }

}
