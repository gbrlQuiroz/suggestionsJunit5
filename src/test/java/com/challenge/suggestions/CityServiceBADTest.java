package com.challenge.suggestions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import com.challenge.suggestions.models.City;
import com.challenge.suggestions.persistences.CityRepository;
import com.challenge.suggestions.services.CityService;
import com.challenge.suggestions.services.impl.CityServiceImpl;
import com.challenge.suggestions.views.CityView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;

import com.challenge.suggestions.converters.CityConverter;
import com.challenge.suggestions.exceptions.*;

public class CityServiceBADTest {
    private CityServiceImpl cityService;
    private CityRepository cityRepository;
    private CityConverter cityConverter;

    @BeforeEach
    void setUp() {
        cityRepository = mock(CityRepository.class);
        // cityService = mock(CityService.class);
        cityService = new CityServiceImpl();
        cityService.setCityRepository(cityRepository);
        cityService.setCityConverter(cityConverter);

    }

    // mvn clean ; mvn test -Dtest=CityServiceTest#shouldSavedCitySuccessfully
    @Test
    void shouldSavedCitySuccessfully() {
        City city = new City(1L, "Pachuca", 33.333, 99.999);
        CityView cV = new CityView(1L, "Pachuca", 33.333, 99.999);

        given(cityRepository.findByName(city.getName())).willReturn(null);
        given(cityRepository.save(city)).willAnswer(invocation -> invocation.getArgument(0));

        // given(cityConverter.toView(city)).willReturn(cV);

        // CityView savedCity = cityService.createCity(cV);
        // System.out.println("--->>>" + savedCity);

        // assertThat(savedCity).isNotNull();
        // assertThat(savedCity).isNull();

        // verify(cityRepository).save(any(City.class));    
        verify(cityRepository).save(city);    
    }

    // mvn clean ; mvn test -Dtest=CityServiceTest#shouldThrowErrorWhenSaveCityWithExistingName
    @Test
    void shouldThrowErrorWhenSaveCityWithExistingName() {
        City city = new City(1L, "Pachuca", 33.333, 99.999);
        CityView cV = new CityView(1L, "Pachuca", 33.333, 99.999);

        given(cityRepository.findByName(city.getName())).willReturn(city);

        assertThrows(ResponseException.class, () -> {
            cityService.createCity(cV);
        });

        verify(cityRepository, never()).save(any(City.class));
    }

}
