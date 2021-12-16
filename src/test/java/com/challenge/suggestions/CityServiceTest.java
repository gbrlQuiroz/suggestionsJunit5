package com.challenge.suggestions;

import org.mockito.Mock;
import org.hibernate.exception.DataException;
// import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.challenge.suggestions.converters.CityConverter;
import com.challenge.suggestions.models.City;
import com.challenge.suggestions.persistences.CityRepository;
import com.challenge.suggestions.services.impl.CityServiceImpl;
import com.challenge.suggestions.views.CityView;
import com.challenge.suggestions.exceptions.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityConverter cityConverter;

    private CityServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new CityServiceImpl();
        underTest.setCityRepository(cityRepository);
        underTest.setCityConverter(cityConverter);
    }

    // mvn clean ; mvn test -Dtest=CityServiceTest#shouldSavedCitySuccessfully
    @Test
    void shouldSavedCitySuccessfully() {
        // given
        City city = new City(1L, "Pachuca", 33.333, 99.999);
        CityView cV = new CityView(1L, "Pachuca", 33.333, 99.999);

        // when
        when(cityRepository.findByName(anyString())).thenReturn(null);
        when(cityRepository.save(any())).thenReturn(city);
        underTest.createCity(cV);

        // then
        verify(cityRepository, times(1)).findByName(anyString());
        verify(cityRepository, times(1)).save(any());

    }

    // mvn clean ; mvn test -Dtest=CityServiceTest#shouldThrowErrorWhenSaveCityWithExistingName
    @Test
    void shouldThrowErrorWhenSaveCityWithExistingName() {
        // given
        City city = new City(1L, "Pachuca", 33.333, 99.999);
        CityView cV = new CityView(1L, "Pachuca", 33.333, 99.999);

        // when
        when(cityRepository.findByName(anyString())).thenReturn(city);

        // then
        assertThrows(ResponseException.class, () -> {
            underTest.createCity(cV);
        });

        verify(cityRepository, never()).save(any(City.class));

    }

    // mvn clean ; mvn test -Dtest=CityServiceTest#exceptionTesting
    @Test
    @Disabled
    void exceptionTesting() {
        CityView cV = new CityView(1L, "Pachuca", 33.333, 99.999);

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.createCity(cV);
        });
        assertEquals("--->>>CityServiceImpl--->createCity--->>>exception", exception.getMessage());
        // verify(cityRepository, never()).save(any(City.class));

        // Throwable throwable = assertThrows(Throwable.class, () -> {
        // underTest.createCity(cV);
        // });
        // assertEquals(Exception.class, throwable.getClass());
        // verify(cityRepository, never()).save(any(City.class));
    }

    // mvn clean ; mvn test -Dtest=CityServiceTest#shouldChangedCitySuccessfully
    @Test
    void shouldChangedCitySuccessfully() {
        // given
        City city = new City(1L, "Pachuca", 33.333, 99.999);
        CityView cV = new CityView(1L, "Pachuca", 33.333, 99.999);

        // when
        when(cityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(city));
        when(cityRepository.save(any())).thenReturn(city);
        underTest.updateCity(cV);

        // then
        verify(cityRepository, times(1)).findById(anyLong());
        verify(cityRepository, times(1)).save(any());
        assertThat(cityRepository.findById(cV.getId())).isEqualTo(Optional.ofNullable(city));

    }

    // mvn clean ; mvn test -Dtest=CityServiceTest#givenExistCityViewNoExistError
    @Test
    void shouldThrowErrorWhenChangeCityWithExistingName() {
        // given
        CityView cV = new CityView(1L, "Pachuca", 33.333, 99.999);

        // when
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseException.class, () -> {
            underTest.updateCity(cV);
        });

        verify(cityRepository, never()).save(any(City.class));

    }
}
