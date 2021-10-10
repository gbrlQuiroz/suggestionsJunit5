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
        assertEquals(199, cuenta);
        log.info("Se cuentan registros: {}", cuenta);
    }

}
